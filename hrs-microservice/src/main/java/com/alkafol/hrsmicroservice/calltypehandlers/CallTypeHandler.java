package com.alkafol.hrsmicroservice.calltypehandlers;

import com.alkafol.hrsmicroservice.dto.CallForTarificationInfo;
import com.alkafol.hrsmicroservice.models.ClientStats;
import com.alkafol.hrsmicroservice.models.TariffInfo;

public interface CallTypeHandler {
    double handlerCallType(ClientStats clientStats, TariffInfo tariffInfo, CallForTarificationInfo callForTarificationInfo);
}
