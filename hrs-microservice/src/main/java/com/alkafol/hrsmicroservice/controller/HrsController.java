package com.alkafol.hrsmicroservice.controller;

import com.alkafol.hrsmicroservice.dto.TarificationResult;
import com.alkafol.hrsmicroservice.services.HrsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Tag(name = "HRS Controller", description = "Provide HRS functionality")
public class HrsController {
    private final HrsService hrsService;

    public HrsController(HrsService hrsService) {
        this.hrsService = hrsService;
    }

    @Operation(summary = "Counts price for each client by passed cdr+")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All prices was counted and returned"),
    })
    @PostMapping(path = "/count_cdrplus", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public TarificationResult countCdrPlus(@RequestParam MultipartFile multipartFile) throws IOException {
        return hrsService.countPriceForClient(multipartFile);
    }

}
