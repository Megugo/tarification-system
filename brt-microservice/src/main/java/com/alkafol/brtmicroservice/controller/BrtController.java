package com.alkafol.brtmicroservice.controller;

import com.alkafol.brtmicroservice.dto.*;
import com.alkafol.brtmicroservice.dto.tarification.ClientTarificationDetails;
import com.alkafol.brtmicroservice.dto.tarification.TarificationResult;
import com.alkafol.brtmicroservice.services.BrtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Tag(name = "BRT Controller", description = "Provide BRT functionality")
public class BrtController {
    private final BrtService brtService;

    public BrtController(BrtService brtService) {
        this.brtService = brtService;
    }

    @Operation(summary = "Start tarification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarification was successfully performed"),
    })
    @PostMapping(path = "/start_tarification", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public TarificationResult startTarification(@RequestParam MultipartFile multipartFile) throws Exception {
        return brtService.handleCdr(multipartFile);
    }

    @Operation(summary = "Generate random clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clients was successfully generated"),
    })
    @PostMapping("/generate_clients")
    public void generateClients(@RequestBody ClientGenerationRequestDto clientGenerationRequestDto){
        brtService.generateClients(clientGenerationRequestDto);
    }

    @Operation(summary = "Add money to abonent by given phone number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance was successfully changed"),
    })
    @PatchMapping("/add_money/{number}/{amount}")
    public AddMoneyResponseDto addMoney(@RequestBody AddMoneyRequestDto addMoneyRequestDto){
        return brtService.addMoney(addMoneyRequestDto);
    }

    @Operation(summary = "Create new abonent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Abonent was successfully created"),
    })
    @PostMapping("/create_abonent")
    public CreateNewClientDto createAbonent(@RequestBody CreateNewClientDto createNewClientDto){
        return brtService.createAbonent(createNewClientDto);
    }

    @Operation(summary = "Change tariff for client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tariff was successfully changed"),
    })
    @PatchMapping("/change_tariff/{phoneNumber}/{newTariff}")
    public ChangeTariffResponseDto changeTariff(@RequestBody ChangeTariffRequestDto changeTariffRequestDto){
        return brtService.changeTariff(changeTariffRequestDto);
    }

    @Operation(summary = "Check client existence by phone number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check was performed."),
    })
    @GetMapping("/check_client_existence/{phoneNumber}")
    public boolean checkClientExistence(@PathVariable long phoneNumber){
        return brtService.checkClientExistence(phoneNumber);
    }

    @Operation(summary = "Get report for given client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report was successfully generated"),
    })
    @GetMapping("/get_client_report/{phoneNumber}")
    public ClientTarificationDetails getClientReport(@PathVariable long phoneNumber) throws IOException {
        return brtService.getClientReport(phoneNumber);
    }
}
