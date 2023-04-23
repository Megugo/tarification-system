package com.alkafol.cdrmicroservice.controllers;

import com.alkafol.cdrmicroservice.services.CdrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "CDR controller", description = "Provide functionality for getting CDR")
public class CdrController {
    CdrService cdrService;

    public CdrController(CdrService cdrService) {
        this.cdrService = cdrService;
    }

    @Operation(summary = "Prepare CDR and send it to BRT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CDR was successfully created and sent to BRT"),
    })
    @GetMapping("/prepare_cdr")
    public String prepareCdr(){
        return cdrService.sendCdr();
    }
}
