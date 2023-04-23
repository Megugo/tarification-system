package com.alkafol.hrsmicroservice.calltypehandlers;

import com.alkafol.hrsmicroservice.dto.CallForTarificationInfo;
import com.alkafol.hrsmicroservice.models.ClientStats;
import com.alkafol.hrsmicroservice.models.TariffInfo;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component("01")
public class OutgoingCallTypeHandler implements CallTypeHandler{

    @Override
    public double handlerCallType(ClientStats clientStats, TariffInfo tariffInfo, CallForTarificationInfo callForTarificationInfo) {
        double callPrice = 0;

        // вычисление длительности звонка в минутах
        int durationInMinutes = (int) ChronoUnit.MINUTES.between(
                callForTarificationInfo.getStartingTime(),
                callForTarificationInfo.getEndingTime()
        ) + 1;

        // не осталось минут по тарифу
        if (clientStats.getLeftedOutgoingSpecialMinutes() == 0){
            callPrice += durationInMinutes * tariffInfo.getDefaultOutgoingPrice();
        }
        else {
            // минуты по тарифу частично покрывают звонок
            if (clientStats.getLeftedOutgoingSpecialMinutes() < durationInMinutes){
                callPrice += clientStats.getLeftedOutgoingSpecialMinutes() * tariffInfo.getSpecialMinutesOutgoingPrice();
                callPrice += (durationInMinutes - clientStats.getLeftedOutgoingSpecialMinutes()) * tariffInfo.getDefaultOutgoingPrice();
                clientStats.setLeftedOutgoingSpecialMinutes(0);
                // считаем вместе входящие и исходящие минуты по тарифу, когда нет разделения
                if (!tariffInfo.isSpecialMinutesSplitted()){
                    clientStats.setLeftedIncomingSpecialMinutes(0);
                }
            }
            // минуты по тарифу полностью покрывают звонок
            else{
                callPrice += durationInMinutes * tariffInfo.getSpecialMinutesOutgoingPrice();
                clientStats.setLeftedOutgoingSpecialMinutes(clientStats.getLeftedOutgoingSpecialMinutes() - durationInMinutes);
                // считаем вместе входящие и исходящие минуты по тарифу, когда нет разделения
                if (!tariffInfo.isSpecialMinutesSplitted()) {
                    clientStats.setLeftedIncomingSpecialMinutes(clientStats.getLeftedIncomingSpecialMinutes() - durationInMinutes);
                }
            }
        }

        return callPrice;
    }
}
