package com.alkafol.cdrmicroservice.services;

import com.alkafol.cdrmicroservice.cdrsource.CdrProvider;
import org.springframework.stereotype.Service;

@Service
public class CdrService {
    private CdrProvider cdrProvider;

    public CdrService(CdrProvider cdrProvider) {
        this.cdrProvider = cdrProvider;
    }

    public void getCdr(){
        cdrProvider.getCdrFile();
    }
}
