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
    private Map<String, ClientInfo> createClientInfoMap(File file){
        Map<String, ClientInfo> numberToCallsMap = new HashMap<>();

        try(Scanner in = new Scanner(file)) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] data = line.split("[ ,]+");
                String number = data[1];

                if (numberToCallsMap.containsKey(number)) {
                    numberToCallsMap.get(number).getCallInfoList().add(new CallForTarificationInfo(
                            data[0],
                            LocalDateTime.parse(data[2]),
                            LocalDateTime.parse(data[3])
                    ));
                } else {
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
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return numberToCallsMap;
    }

    public TarificationResult handleCdrPlus(MultipartFile multipartFile) throws IOException {
        // парсинг MultipartFile в обычный файл
        Path filepath = Path.of("cdr+.txt");
        multipartFile.transferTo(filepath);
        File file = new File(String.valueOf(filepath));

        Map<String, ClientInfo> numberToCallsMap = createClientInfoMap(file);
        List<ClientTarificationResult> clientTarificationResultList = new ArrayList<>();

        // вычисление трат для каждого клиента
        for (Map.Entry<String, ClientInfo> entry : numberToCallsMap.entrySet()) {
            String number = entry.getKey();
            ClientInfo clientInfo = entry.getValue();

            // расчёт цены каждого звонка
            List<CallInfo> countedCalls = handleClient(clientInfo);

            // добавление тарификации для отдельного клиента в общий список
            clientTarificationResultList.add(new ClientTarificationResult(
                    number,
                    clientInfo.getTariffInfo().getTariffId(),
                    countedCalls,
                    countedCalls.stream().map(CallInfo::getCost).mapToDouble(Double::doubleValue).sum(),
                    MonetaryUnits.RUB.getName()
            ));
        }

        return new TarificationResult(clientTarificationResultList);
    }

    // метод расчёта цены каждого звонка
    public List<CallInfo> handleClient(ClientInfo clientInfo){
        List<CallInfo> callInfoList = new ArrayList<>();

        // сортировка по времени звонков
        List<CallForTarificationInfo> callForTarificationInfos = clientInfo.getCallInfoList();
        callForTarificationInfos.sort(Comparator.comparing(CallForTarificationInfo::getStartingTime));

        // статистика клиента - отслеживает остаток минут по тарифам
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
            callInfoList.add(new CallInfo(
                    callForTarificationInfo.getCallType(),
                    callForTarificationInfo.getStartingTime(),
                    callForTarificationInfo.getEndingTime(),
                    duration,
                    callPrice
            ));
        }

        return callInfoList;
    }
}
