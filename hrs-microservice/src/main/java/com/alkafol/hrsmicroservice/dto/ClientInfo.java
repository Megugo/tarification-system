package com.alkafol.hrsmicroservice.dto;

import com.alkafol.hrsmicroservice.models.TariffInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ClientInfo {
    TariffInfo tariffInfo;
    List<CallForTarificationInfo> callInfoList;
}
