package com.alkafol.hrsmicroservice.calltypehandlers;

import com.alkafol.hrsmicroservice.dto.CallForTarificationInfo;
import com.alkafol.hrsmicroservice.models.ClientStats;
import com.alkafol.hrsmicroservice.models.TariffInfo;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component("02")
public class IncomingCallsHandler implements CallTypeHandler {

    @Override
    public double handlerCallType(ClientStats clientStats, TariffInfo tariffInfo, CallForTarificationInfo callForTarificationInfo) {
        double callPrice = 0;

        // вычисление длительности звонка в минутах
        int durationInMinutes = (int) ChronoUnit.MINUTES.between(
                callForTarificationInfo.getStartingTime(),
                callForTarificationInfo.getEndingTime()
        ) + 1;

        // не осталось минут по тарифу
        if (clientStats.getLeftedIncomingSpecialMinutes() == 0){
            callPrice += durationInMinutes * tariffInfo.getDefaultIncomingPrice();
        }
        else {
            // минуты по тарифу частично покрывают звонок
            if (clientStats.getLeftedIncomingSpecialMinutes() < durationInMinutes){
                callPrice += clientStats.getLeftedIncomingSpecialMinutes() * tariffInfo.getSpecialMinutesIncomingPrice();
                callPrice += (durationInMinutes - clientStats.getLeftedIncomingSpecialMinutes()) * tariffInfo.getDefaultIncomingPrice();
                clientStats.setLeftedIncomingSpecialMinutes(0);

                // считаем вместе входящие и исходящие минуты по тарифу, когда нет разделения
                if (!tariffInfo.isSpecialMinutesSplitted()){
                    clientStats.setLeftedOutgoingSpecialMinutes(0);
                }
            }
            // минуты по тарифу могут полностью покрыть звонок
            else{
                callPrice += durationInMinutes * tariffInfo.getSpecialMinutesIncomingPrice();
                clientStats.setLeftedIncomingSpecialMinutes(clientStats.getLeftedIncomingSpecialMinutes() - durationInMinutes);
                // считаем вместе входящие и исходящие минуты по тарифу, когда нет разделения
                if (!tariffInfo.isSpecialMinutesSplitted()) {
                    clientStats.setLeftedOutgoingSpecialMinutes(clientStats.getLeftedOutgoingSpecialMinutes() - durationInMinutes);
                }
            }
        }

        return callPrice;
    }
}
