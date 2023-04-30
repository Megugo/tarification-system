package com.alkafol.srmmicroservice.services;

import com.alkafol.srmmicroservice.dto.clientdto.ClientRegistrationDto;
import com.alkafol.srmmicroservice.dto.managerdto.ManagerRegistrationDto;
import com.alkafol.srmmicroservice.models.Role;
import com.alkafol.srmmicroservice.models.User;
import com.alkafol.srmmicroservice.repository.UserRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService underTest;

    @Test
    public void testCreateNewManagerDuplicationException() {
        // given
        String login = "AntonAB";
        String password = "1234";
        ManagerRegistrationDto managerRegistrationDto = new ManagerRegistrationDto(
                login,
                password
        );

        underTest = spy(underTest);
        doReturn(Boolean.FALSE).when(underTest).isUsernameFree(login);

        // when
        // then
        assertThrows(DuplicateKeyException.class, () -> underTest.createNewManager(managerRegistrationDto));
    }

    @Test
    public void testCreateNewManager() {
        // given
        String login = "AntonAB";
        String password = "1234";
        String encodedPassword = "encoded_password";
        ManagerRegistrationDto managerRegistrationDto = new ManagerRegistrationDto(
                login,
                password
        );

        underTest = spy(underTest);
        doReturn(Boolean.TRUE).when(underTest).isUsernameFree(login);
        doReturn(encodedPassword).when(passwordEncoder).encode(password);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        // when
        underTest.createNewManager(managerRegistrationDto);

        // then
        verify(passwordEncoder).encode(password);
        verify(userRepository).save(userArgumentCaptor.capture());

        assertEquals(Role.MANAGER, userArgumentCaptor.getValue().getRole());
        assertEquals(encodedPassword, userArgumentCaptor.getValue().getPassword());
        assertEquals(login, userArgumentCaptor.getValue().getUsername());
    }

    @Test
    public void testCreateNewClientDuplicationException() {
        // given
        Long phoneNumber = 89991234567L;
        String password = "1234";
        ClientRegistrationDto clientRegistrationDto = new ClientRegistrationDto(
                phoneNumber,
                password
        );

        underTest = spy(underTest);
        doReturn(Boolean.FALSE).when(underTest).isUsernameFree(String.valueOf(phoneNumber));

        // when
        // then
        assertThrows(DuplicateKeyException.class, () -> underTest.createNewClient(clientRegistrationDto));
    }

    @Test
    public void testCreateNewClient() {
        // given
        Long phoneNumber = 89991234567L;
        String password = "1234";
        String encodedPassword = "encoded_password";
        ClientRegistrationDto clientRegistrationDto = new ClientRegistrationDto(
                phoneNumber,
                password
        );

        underTest = spy(underTest);
        doReturn(Boolean.TRUE).when(underTest).isUsernameFree(String.valueOf(phoneNumber));
        doReturn(encodedPassword).when(passwordEncoder).encode(password);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        // when
        underTest.createNewClient(clientRegistrationDto);

        // then
        verify(passwordEncoder).encode(password);
        verify(userRepository).save(userArgumentCaptor.capture());

        assertEquals(Role.CLIENT, userArgumentCaptor.getValue().getRole());
        assertEquals(encodedPassword, userArgumentCaptor.getValue().getPassword());
        assertEquals(phoneNumber.toString(), userArgumentCaptor.getValue().getUsername());
    }
}
