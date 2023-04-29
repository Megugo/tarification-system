package com.alkafol.srmmicroservice.services;

import com.alkafol.srmmicroservice.dto.clientdto.ClientRegistrationDto;
import com.alkafol.srmmicroservice.dto.clientdto.ReportResponseDto;
import com.alkafol.srmmicroservice.models.User;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {
    @Mock
    UserService userService;

    @Mock
    RestTemplate restTemplate;

    @Mock
    Environment env;

    @InjectMocks
    ClientService underTest;

    @Test
    public void testRegisterUserWithoutNumberInSrm() {
        // given
        String brtMicroserviceAddress = "localhost:8080";
        long clientPhoneNumber = 89991234567L;
        String clientPassword = "1234";
        ClientRegistrationDto clientRegistrationDto = new ClientRegistrationDto(
                clientPhoneNumber,
                clientPassword
        );

        doReturn(brtMicroserviceAddress).when(env).getProperty("brt.microservice.address");
        doReturn(Boolean.FALSE).when(restTemplate).getForObject(brtMicroserviceAddress + "/check_client_existence/" + clientPhoneNumber, Boolean.class);

        // when
        // then
        assertThrows(EntityNotFoundException.class, () -> underTest.register(clientRegistrationDto));
    }

    @Test
    public void testRegisterClientWithNumberInSrm() {
        // given
        String brtMicroserviceAddress = "localhost:8080";
        long clientPhoneNumber = 89991234567L;
        String clientPassword = "1234";

        ClientRegistrationDto clientRegistrationDto = new ClientRegistrationDto(
                clientPhoneNumber,
                clientPassword
        );

        doReturn(brtMicroserviceAddress).when(env).getProperty("brt.microservice.address");
        doReturn(Boolean.TRUE).when(restTemplate).getForObject(brtMicroserviceAddress + "/check_client_existence/" + clientPhoneNumber, Boolean.class);

        // when
        underTest.register(clientRegistrationDto);

        // then
        verify(userService).createNewClient(clientRegistrationDto);
    }

    @Test
    public void testGetReportForAnotherUser(){
        // given
        long requestedPhoneNumber = 89991234567L;
        long userPhoneNumber = 89991111111L;

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        doReturn(authentication).when(securityContext).getAuthentication();
        SecurityContextHolder.setContext(securityContext);
        User user = mock(User.class);
        doReturn(user).when(authentication).getPrincipal();
        doReturn(String.valueOf(userPhoneNumber)).when(user).getUsername();

        // when
        // then
        assertThrows(IllegalAccessException.class, () -> underTest.getReport(requestedPhoneNumber));
    }

    @Test
    public void testGetReportForUserWhoRequestsIt() throws IllegalAccessException {
        // given
        long userPhoneNumber = 89991234567L;
        String brtMicroserviceAddress = "localhost:8080";

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        doReturn(authentication).when(securityContext).getAuthentication();
        SecurityContextHolder.setContext(securityContext);
        User user = mock(User.class);

        doReturn(user).when(authentication).getPrincipal();
        doReturn(String.valueOf(userPhoneNumber)).when(user).getUsername();
        doReturn(brtMicroserviceAddress).when(env).getProperty("brt.microservice.address");

        // when
        underTest.getReport(userPhoneNumber);

        // then
        verify(restTemplate).getForObject(brtMicroserviceAddress + "/get_client_report/" + userPhoneNumber, ReportResponseDto.class);
    }
}
