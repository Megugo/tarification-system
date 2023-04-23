package com.alkafol.srmmicroservice.services;

import com.alkafol.srmmicroservice.dto.managerdto.*;
import com.alkafol.srmmicroservice.models.Role;
import com.alkafol.srmmicroservice.models.User;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ManagerService {
    private final UserService userService;
    private final RestTemplate restTemplate;
    private final Environment env;

    public ManagerService(UserService userService, RestTemplate restTemplate, Environment env) {
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.env = env;
    }

    public ChangeTariffResponseDto changeTariff(ChangeTariffRequestDto changeTariffRequestDto) {
        return restTemplate.patchForObject(
                env.getProperty("brt.microservice.address") + "/change_tariff/" + changeTariffRequestDto.getPhoneNumber()
                        + "/" + changeTariffRequestDto.getTariffId(),
                changeTariffRequestDto,
                ChangeTariffResponseDto.class
        );
    }

    public CreateNewClientDto createNewClient(CreateNewClientDto createNewClientDto) {
        return restTemplate.postForObject(
                env.getProperty("brt.microservice.address") + "/create_abonent",
                createNewClientDto,
                CreateNewClientDto.class
        );
    }

    public TarificationResult startBilling() {
        return restTemplate.getForObject(
                env.getProperty("cdr.microservice.address") + "/prepare_cdr",
                TarificationResult.class
        );
    }


    // регистрация менеджера (логин + пароль)
    public void register(ManagerRegistrationDto managerRegistrationDto) {
        User user = new User(
                managerRegistrationDto.getLogin(),
                managerRegistrationDto.getPassword(),
                Role.MANAGER
        );

        userService.createNewUser(user);
    }
}
