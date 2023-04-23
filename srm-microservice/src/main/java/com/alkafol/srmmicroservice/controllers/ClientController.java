package com.alkafol.srmmicroservice.controllers;

import com.alkafol.srmmicroservice.dto.clientdto.AddMoneyRequestDto;
import com.alkafol.srmmicroservice.dto.clientdto.AddMoneyResponseDto;
import com.alkafol.srmmicroservice.dto.clientdto.ClientRegistrationDto;
import com.alkafol.srmmicroservice.dto.clientdto.ReportResponseDto;
import com.alkafol.srmmicroservice.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/abonent")
@Tag(name = "Clients controller", description = "Provide functionality for clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Add money to client account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance was successfully changed"),
            @ApiResponse(responseCode = "401", description = "Wrong credentials"),
            @ApiResponse(responseCode = "403", description = "No permissions to resource. Might also be server-side error")
    })
    @PatchMapping("/pay")
    public AddMoneyResponseDto addMoney(@Valid @RequestBody AddMoneyRequestDto addMoneyDto){
        return clientService.addMoney(addMoneyDto);
    }

    @Operation(summary = "Get detailed report for given phone number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report was successfully fetched"),
            @ApiResponse(responseCode = "401", description = "Wrong credentials"),
            @ApiResponse(responseCode = "403", description = "No permissions to resource. Might also be server-side error")
    })
    @GetMapping("/report/{phoneNumber}")
    public ReportResponseDto getReport(@PathVariable long phoneNumber) throws IllegalAccessException {
        return clientService.getReport(phoneNumber);
    }

    @Operation(summary = "Register user for API access. User must be a client (have a phone number).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was successfully registered")
    })
    @PostMapping("/register")
    public void register(@RequestBody ClientRegistrationDto clientRegistrationDto){
        clientService.register(clientRegistrationDto);
    }
}
