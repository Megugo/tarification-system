package com.alkafol.srmmicroservice.services;

import com.alkafol.srmmicroservice.dto.clientdto.AddMoneyRequestDto;
import com.alkafol.srmmicroservice.dto.clientdto.AddMoneyResponseDto;
import com.alkafol.srmmicroservice.dto.clientdto.ClientRegistrationDto;
import com.alkafol.srmmicroservice.dto.clientdto.ReportResponseDto;
import com.alkafol.srmmicroservice.models.Role;
import com.alkafol.srmmicroservice.models.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientService {
    private final UserService userService;
    private final RestTemplate restTemplate;
    private final Environment env;

    public ClientService(UserService userService, RestTemplate restTemplate, Environment env) {
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.env = env;
    }

    public AddMoneyResponseDto addMoney(AddMoneyRequestDto addMoneyDto) {
        return restTemplate.patchForObject(
                env.getProperty("brt.microservice.address") + "/add_money/" + addMoneyDto.getPhoneNumber() + "/" + addMoneyDto.getMoney(),
                addMoneyDto,
                AddMoneyResponseDto.class
        );
    }

    public ReportResponseDto getReport(long phoneNumber) throws IllegalAccessException {
        // проверка что пользователь не хочет взять чужой отчёт
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (Long.parseLong(((User) auth.getPrincipal()).getUsername()) != phoneNumber){
            throw new IllegalAccessException();
        }

        // получение отчёта из brt
        return restTemplate.getForObject(
                env.getProperty("brt.microservice.address") + "/get_client_report/" + phoneNumber,
                ReportResponseDto.class
        );
    }

    // регистрация клиента (логин + пароль)
    public void register(ClientRegistrationDto clientRegistrationDto) {
        Boolean isNumberExists = restTemplate.getForObject(
                env.getProperty("brt.microservice.address") + "/check_client_existence/" + clientRegistrationDto.getPhoneNumber(),
                Boolean.class
        );

        // проверка что пользователь уже является клиентом и может зарегистрироваться для запросов к API
        if (Boolean.FALSE.equals(isNumberExists)){
            throw new EntityNotFoundException();
        }

        userService.createNewClient(clientRegistrationDto);
    }
}
