package com.alkafol.brtmicroservice.services;

import com.alkafol.brtmicroservice.dto.*;
import com.alkafol.brtmicroservice.dto.tarification.ClientTarificationDetails;
import com.alkafol.brtmicroservice.dto.tarification.TarificationResponseDto;
import com.alkafol.brtmicroservice.dto.tarification.TarificationResult;
import com.alkafol.brtmicroservice.dto.tarification.TarificationResultEntry;
import com.alkafol.brtmicroservice.models.Call;
import com.alkafol.brtmicroservice.entities.Client;
import com.alkafol.brtmicroservice.entities.Tariff;
import com.alkafol.brtmicroservice.repositories.ClientRepository;
import com.alkafol.brtmicroservice.repositories.TariffRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

@Service
public class BrtService {
    private final ClientRepository clientRepository;
    private final TariffRepository tariffRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Environment env;

    public BrtService(ClientRepository clientRepository, TariffRepository tariffRepository, RestTemplate restTemplate, ObjectMapper objectMapper, Environment env) {
        this.clientRepository = clientRepository;
        this.restTemplate = restTemplate;
        this.tariffRepository = tariffRepository;
        this.objectMapper = objectMapper;
        this.env = env;
    }

    public void convertToCdrPlus() throws Exception {
        Scanner in = new Scanner(new File("cdr.txt"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        File cdrPlus = new File("cdr+.txt");
        cdrPlus.createNewFile();
        FileWriter fw = new FileWriter(cdrPlus);

        String regex = "^(?:01|02),(?:\\+?\\d{10,13}),\\d{14},\\d{14}$";
        Pattern pattern = Pattern.compile(regex);
        while (in.hasNextLine()) {
            String line = in.nextLine();

            // проверка валидности записи
            if (!pattern.matcher(line).matches()){
                continue;
            }

            String[] data = line.split("[ ,]+");
            Long number = Long.parseLong(data[1]);
            String callType = data[0];
            LocalDateTime startingTime = LocalDateTime.parse(data[2], formatter);
            LocalDateTime endingTime = LocalDateTime.parse(data[3], formatter);

            // проверка валидности времени в записи
            if (startingTime.isAfter(endingTime)){
                continue;
            }

            // проверка что клиент есть и его баланс > 0
            Client client = clientRepository.findByNumber(number);
            if (client == null || client.getBalance() <= 0){
                continue;
            }

            // добавление записи в cdr+
            Call call = new Call(callType, startingTime, endingTime);
            addToCdrPlus(fw, client, call);
        }

        fw.close();
    }

    private void addToCdrPlus(FileWriter fw, Client client, Call call) throws Exception{
        fw.write(call.getCallType() + " "
                + client.getNumber() + " "
                + call.getStartingTime() + " "
                + call.getEndingTime() + " "
                + client.getTariff().getId() + " "
                + client.getTariff().getBasePrice() + " "
                + client.getTariff().getSpecialMinutesIncomingAmount() + " "
                + client.getTariff().getSpecialMinutesOutgoingAmount() + " "
                + client.getTariff().getSpecialMinutesIncomingPrice() + " "
                + client.getTariff().getSpecialMinutesOutgoingPrice() + " "
                + client.getTariff().getDefaultIncomingPrice() + " "
                + client.getTariff().getDefaultOutgoingPrice() + " "
                + client.getTariff().getSpecialMinutesSplitted() + "\n"
        );
    }

    public TarificationResult processHrsOutput(TarificationResponseDto tarificationResponseDto) throws IOException {
        // создание файла отчёта из DTO
        objectMapper.writeValue(Paths.get("report.json").toFile(), tarificationResponseDto);

        TarificationResult tarificationResult = new TarificationResult(new ArrayList<>());

        // изменение баланса и удаление лишних параметров тарификаии
        for (ClientTarificationDetails clientTarificationDetails : tarificationResponseDto.getTarification()){
            Client client = clientRepository.findByNumber(clientTarificationDetails.getPhoneNumber());
            client.setBalance(client.getBalance() - clientTarificationDetails.getTotalCost());
            clientRepository.save(client);

            TarificationResultEntry tarificationResultEntry = new TarificationResultEntry(
                client.getNumber(),
                client.getBalance()
            );
            tarificationResult.getNumbers().add(tarificationResultEntry);
        }

        return tarificationResult;
    }

    public TarificationResult handleCdr(MultipartFile multipartFile) throws Exception {
        // создание cdr
        Path filepath = Path.of("cdr.txt");
        multipartFile.transferTo(filepath);

        // создание cdr+
        convertToCdrPlus();

        // отправка cdr+ в hrs
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("multipartFile", new FileSystemResource("cdr+.txt"));

        String destUrl = env.getProperty("hrs.microservice.address") + "/count_cdrplus";
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        TarificationResponseDto response = restTemplate.postForObject(destUrl, requestEntity, TarificationResponseDto.class);

        return processHrsOutput(response);
    }

    public void generateClients(ClientGenerationRequestDto clientGenerationRequestDto) {
        clientRepository.deleteAll();
        List<Tariff> allTariffs = tariffRepository.findAll();

        // генерация валидных клиентов (которые гарантированно есть в cdr)
        for (Long number : clientGenerationRequestDto.getBaseNumbers()){
            Client client = new Client(
                number,
                allTariffs.get((int) (Math.random() * allTariffs.size()))
            );

            client.setBalance(Math.random() * (1000 + 1000) - 1000);
            clientRepository.save(client);
        }

        // генерация случайных клиентов
        for (int i = 0; i < clientGenerationRequestDto.getNumbersAmount() - clientGenerationRequestDto.getBaseNumbers().size(); ++i){
            Long number = 71000000000L + (long) (Math.random() * ((79999999999L - 71000000000L) + 1L));

            Client client = new Client(
                    number,
                    allTariffs.get((int) (Math.random() * allTariffs.size()))
            );
            client.setBalance(Math.random() * (1000 + 1000) - 1000);
            clientRepository.save(client);
        }
    }

    // добавление денег на баланс
    public AddMoneyResponseDto addMoney(AddMoneyRequestDto addMoneyRequestDto) {
        Client client = clientRepository.findByNumber(addMoneyRequestDto.getPhoneNumber());
        if (client == null){
            throw new EntityNotFoundException();
        }

        client.setBalance(client.getBalance() + addMoneyRequestDto.getMoney());

        clientRepository.save(client);

        return new AddMoneyResponseDto(
                addMoneyRequestDto.getPhoneNumber(),
                client.getBalance()
        );
    }

    // создание клиента (по dto от менеджера)
    public ClientDto createAbonent(ClientDto createNewClientDto) {
        if (clientRepository.findByNumber(createNewClientDto.getPhoneNumber()) != null){
            throw new EntityExistsException();
        }

        Tariff tariff = tariffRepository.findById(createNewClientDto.getTariffId()).orElseThrow(EntityNotFoundException::new);

        Client client = new Client(
                createNewClientDto.getPhoneNumber(),
                tariff
        );
        client.setBalance(createNewClientDto.getBalance());

        clientRepository.save(client);

        return new ClientDto(
                client.getNumber(),
                client.getTariff().getId(),
                client.getBalance()
        );
    }

    // изменение тарифа клиента (по dto от менеджера)
    public ChangeTariffResponseDto changeTariff(ChangeTariffRequestDto changeTariffRequestDto) {
        Client client = clientRepository.findByNumber(changeTariffRequestDto.getPhoneNumber());
        if (client == null){
            throw new EntityNotFoundException();
        }

        Tariff tariff = tariffRepository.findById(changeTariffRequestDto.getTariffId()).orElseThrow(EntityNotFoundException::new);
        client.setTariff(tariff);

        clientRepository.save(client);

        return new ChangeTariffResponseDto(
                client.getNumber(),
                client.getTariff().getId()
        );
    }

    // проверка наличия клиента
    public boolean checkClientExistence(long phoneNumber) {
        return (clientRepository.findByNumber(phoneNumber) != null);
    }

    // получение тарификации для отдельного клиента
    public ClientTarificationDetails getClientReport(long phoneNumber) throws IOException {
        File report = new File("report.json");
        JsonNode root = objectMapper.readTree(report);

        // парсинг json отчёта о полной тарификации
        for (JsonNode node : root.get("tarification")) {
            if (node.get("phoneNumber").longValue() == phoneNumber){
                return objectMapper.treeToValue(node, ClientTarificationDetails.class);
            }
        }

        return null;
    }
}
