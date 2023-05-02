package com.alkafol.srmmicroservice.services;

import com.alkafol.srmmicroservice.dto.managerdto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTests {
    @Mock
    UserService userService;

    @Mock
    RestTemplate restTemplate;

    @Mock
    Environment env;

    @InjectMocks
    ManagerService underTest;

    @Test
    public void testCreateNewClient() {
        // given
        String brtMicroserviceAddress = "localhost:8080";
        String phoneNumber = "89991234567";
        String tariffId = "06";
        int balance = 100;
        ClientDto clientDto = new ClientDto(
                phoneNumber,
                tariffId,
                balance
        );

        doReturn(brtMicroserviceAddress).when(env).getProperty("brt.microservice.address");

        // when
        underTest.createNewClient(clientDto);

        // then
        verify(restTemplate).postForObject(
                brtMicroserviceAddress + "/create_abonent",
                clientDto,
                ClientDto.class
        );
    }

    @Test
    public void testStartBilling(){
        // given
        String cdrMicroserviceAddress = "localhost:8081";

        doReturn(cdrMicroserviceAddress).when(env).getProperty("cdr.microservice.address");

        // when
        underTest.startBilling();

        // then
        verify(restTemplate).getForObject(cdrMicroserviceAddress + "/prepare_cdr", TarificationResult.class);
    }

    @Test
    public void testRegisterManager(){
        // given
        String login = "AntonAB";
        String password = "1234";
        ManagerRegistrationDto managerRegistrationDto = new ManagerRegistrationDto(
                login,
                password
        );

        // when
        underTest.register(managerRegistrationDto);

        // then
        verify(userService).createNewManager(managerRegistrationDto);
    }

    @Test
    public void testChangeTariff(){
        // given
        String brtMicroserviceAddress = "localhost:8080";
        String phoneNumber = "89991234567";
        String tariffId = "06";
        ChangeTariffRequestDto changeTariffRequestDto = new ChangeTariffRequestDto(
                phoneNumber,
                tariffId
        );

        doReturn(brtMicroserviceAddress).when(env).getProperty("brt.microservice.address");

        // when
        underTest.changeTariff(changeTariffRequestDto);

        // then
        verify(restTemplate).patchForObject(
                brtMicroserviceAddress + "/change_tariff",
                changeTariffRequestDto,
                ChangeTariffResponseDto.class
        );
    }
}
