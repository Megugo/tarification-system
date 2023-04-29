package com.alkafol.srmmicroservice.services;

import com.alkafol.srmmicroservice.dto.clientdto.ClientRegistrationDto;
import com.alkafol.srmmicroservice.dto.managerdto.ManagerRegistrationDto;
import com.alkafol.srmmicroservice.models.Role;
import com.alkafol.srmmicroservice.models.User;
import com.alkafol.srmmicroservice.repository.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public void createNewClient(ClientRegistrationDto clientRegistrationDto){
        if (!isUsernameFree(String.valueOf(clientRegistrationDto.getPhoneNumber()))){
            throw new DuplicateKeyException("Username already exists");
        }

        User user = new User(
                String.valueOf(clientRegistrationDto.getPhoneNumber()),
                passwordEncoder.encode(clientRegistrationDto.getPassword()),
                Role.CLIENT
        );

        userRepository.save(user);
    }

    public void createNewManager(ManagerRegistrationDto managerRegistrationDto){
        if (!isUsernameFree(managerRegistrationDto.getLogin())){
            throw new DuplicateKeyException("Username already exists");
        }

        User user = new User(
                managerRegistrationDto.getLogin(),
                passwordEncoder.encode(managerRegistrationDto.getPassword()),
                Role.MANAGER
        );

        userRepository.save(user);
    }

    public Boolean isUsernameFree(String username){
        return !userRepository.existsById(username);
    }
}
