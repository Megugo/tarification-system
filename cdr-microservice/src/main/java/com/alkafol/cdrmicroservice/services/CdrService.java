package com.alkafol.cdrmicroservice.services;

import com.alkafol.cdrmicroservice.cdrsource.CdrProvider;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Service
public class CdrService {
    private final CdrProvider cdrProvider;
    private final RestTemplate restTemplate;
    private final Environment env;

    public CdrService(CdrProvider cdrProvider, RestTemplate restTemplate, Environment env) {
        this.cdrProvider = cdrProvider;
        this.restTemplate = restTemplate;
        this.env = env;
    }

    // получаем cdr из заданного cdrProvider (в нашем случае случайная генерация)
    private void getCdr(){
        cdrProvider.getCdrFile();
    }

    @EventListener(ApplicationReadyEvent.class)
    public String sendCdr(){
        getCdr();

        // отправка cdr -> brt в MultipartFile
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("multipartFile", new FileSystemResource("cdr.txt"));

        String destUrl = env.getProperty("brt.microservice.address") + "/start_tarification";
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(destUrl, requestEntity, String.class);

        return response.getBody();
    }
}
