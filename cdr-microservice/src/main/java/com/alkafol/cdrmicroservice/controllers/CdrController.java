package com.alkafol.cdrmicroservice.controllers;

import com.alkafol.cdrmicroservice.services.CdrService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CdrController {
    CdrService cdrService;

    public CdrController(CdrService cdrService) {
        this.cdrService = cdrService;
    }

    @GetMapping("get_cdr")
    public void getCdr(){
        cdrService.getCdr();;
    }
}
