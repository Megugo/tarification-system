package com.alkafol.hrsmicroservice.services;

import com.alkafol.hrsmicroservice.calltypehandlers.CallTypeHandler;
import com.alkafol.hrsmicroservice.dto.*;
import com.alkafol.hrsmicroservice.models.ClientStats;
import com.alkafol.hrsmicroservice.models.MonetaryUnits;
import com.alkafol.hrsmicroservice.models.TariffInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class HrsService {
    // Map для типов звонков и их обработчиков
    // Позвоялет легко расширить HrsService добавив новые типы звонков
    Map<String, CallTypeHandler> callTypesHandlers;

    public HrsService(Map<String, CallTypeHandler> callTypesHandlers) {
        this.callTypesHandlers = callTypesHandlers;
    }

    // создание map: клиент -> список звонков
    private Map<Long, ClientInfo> createClientInfoMap(File file) throws FileNotFoundException {
        Scanner in = new Scanner(file);
        Map<Long, ClientInfo> numberToCallsMap = new HashMap<>();

        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] data = line.split("[ ,]+");
            Long number = Long.parseLong(data[1]);

            if (numberToCallsMap.containsKey(number)){
                numberToCallsMap.get(number).getCallInfoList().add(new CallForTarificationInfo(
                        data[0],
                        LocalDateTime.parse(data[2]),
                        LocalDateTime.parse(data[3])
                ));
            }
            else {
                TariffInfo tariffInfo = new TariffInfo(
                        data[4],
                        Double.parseDouble(data[5]),
                        Integer.parseInt(data[6]),
                        Integer.parseInt(data[7]),
                        Double.parseDouble(data[8]),
                        Double.parseDouble(data[9]),
                        Double.parseDouble(data[10]),
                        Double.parseDouble(data[11]),
                        Boolean.parseBoolean(data[12])
                );
                ClientInfo clientInfo = new ClientInfo(tariffInfo, new ArrayList<>());
                numberToCallsMap.put(number, clientInfo);
            }
        }

        return numberToCallsMap;
    }

    public TarificationResult countPriceForClient(MultipartFile multipartFile) throws IOException {
        // парсинг MultipartFile в обычный файл
        Path filepath = Path.of("hrs-microservice/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(filepath);
        File file = new File(String.valueOf(filepath));

        Map<Long, ClientInfo> numberToCallsMap = createClientInfoMap(file);
        List<ClientTarificationResult> clientTarificationResultList = new ArrayList<>();

        // вычисление трат для каждого клиента
        for (Map.Entry<Long, ClientInfo> entry : numberToCallsMap.entrySet()) {
            Long number = entry.getKey();
            ClientInfo clientInfo = entry.getValue();
            clientTarificationResultList.add(new ClientTarificationResult(
                    number,
                    clientInfo.getTariffInfo().getTariffId(),
                    new ArrayList<>(),
                    0,
                    MonetaryUnits.RUB.getName()
            ));

            // сортировка по времени звонков
            List<CallForTarificationInfo> callForTarificationInfos = clientInfo.getCallInfoList();
            callForTarificationInfos.sort(Comparator.comparing(CallForTarificationInfo::getStartingTime));

            double totalPrice = clientInfo.getTariffInfo().getBasePrice();
            // статистика клиентов - отслеживает остаток минут по тарифам
            ClientStats clientStats = new ClientStats(
                    clientInfo.getTariffInfo().getSpecialMinutesOutgoingAmount(),
                    clientInfo.getTariffInfo().getSpecialMinutesIncomingAmount()
            );

            // цикл по звонкам клиента
            for (CallForTarificationInfo callForTarificationInfo : callForTarificationInfos){

                // обрабатываем вызов (для входящих и исходящих свой отдельный обработчик)
                CallTypeHandler callTypeHandler = callTypesHandlers.get(callForTarificationInfo.getCallType());
                double callPrice = callTypeHandler.handlerCallType(clientStats, clientInfo.getTariffInfo(), callForTarificationInfo);

                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String duration = LocalTime.ofSecondOfDay(ChronoUnit.SECONDS.between(callForTarificationInfo.getStartingTime(), callForTarificationInfo.getEndingTime())).format(timeFormatter);

                // запись о звонке (цена, длительность ...)
                clientTarificationResultList.get(clientTarificationResultList.size() - 1).getPayload().add(new CallInfo(
                        callForTarificationInfo.getCallType(),
                        callForTarificationInfo.getStartingTime(),
                        callForTarificationInfo.getEndingTime(),
                        duration,
                        callPrice
                ));

                totalPrice += callPrice;
            }

            clientTarificationResultList.get(clientTarificationResultList.size() - 1).setTotalCost(totalPrice);
        }

        return new TarificationResult(clientTarificationResultList);
    }
}
