package com.alkafol.srmmicroservice.controllers;

import com.alkafol.srmmicroservice.dto.managerdto.*;
import com.alkafol.srmmicroservice.services.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
@Tag(name = "Manager controller", description = "Provide functionality for managers")
public class ManagerController {
    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @Operation(summary = "Change client tariff")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tariff was successfully changed"),
            @ApiResponse(responseCode = "401", description = "Wrong credentials"),
            @ApiResponse(responseCode = "403", description = "No permissions to resource. Might also be server-side error")
    })
    @PatchMapping("/changeTariff")
    public ChangeTariffResponseDto changeTariff(@RequestBody ChangeTariffRequestDto changeTariffRequestDto){
        return managerService.changeTariff(changeTariffRequestDto);
    }

    @Operation(summary = "Create new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client was successfully created"),
            @ApiResponse(responseCode = "401", description = "Wrong credentials"),
            @ApiResponse(responseCode = "403", description = "No permissions to resource. Might also be server-side error")
    })
    @PostMapping("/abonent")
    public CreateNewClientDto createNewClient(@Valid @RequestBody CreateNewClientDto createNewClientDto){
        return managerService.createNewClient(createNewClientDto);
    }

    @Operation(summary = "Start billing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Billing report was successfully generated and received"),
            @ApiResponse(responseCode = "401", description = "Wrong credentials"),
            @ApiResponse(responseCode = "403", description = "No permissions to resource. Might also be server-side error")
    })
    @PatchMapping("/billing")
    public TarificationResult startBilling(@RequestBody TarificationRequestDto tarificationRequestDto){
        return managerService.startBilling();
    }

    @Operation(summary = "Register new manager")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Manager was successfully registered"),
    })
    @PostMapping("/register")
    public void register(@RequestBody ManagerRegistrationDto managerRegistrationDto){
        managerService.register(managerRegistrationDto);
    }
}
