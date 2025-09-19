package com.app.agendai.services;

import com.app.agendai.dtos.*;
import com.app.agendai.entities.User;
import com.app.agendai.entities.UserRole;
import com.app.agendai.infra.security.TokenService;
import com.app.agendai.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClientService clientService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EnterpriseService enterpriseService;

    public String login(LoginRequest loginRequest){
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken( (User) auth.getPrincipal());
        return token;
    }

    public void registerClient(RegisterClientRequest registerClientRequest){
        if (userRepository.findByLogin(registerClientRequest.login()) != null){

        }

        String encryptedPasword = new BCryptPasswordEncoder().encode(registerClientRequest.password());

        User user = new User(registerClientRequest.login(), encryptedPasword, UserRole.USER);
        User user1 = userRepository.save(user);

        clientService.save(new ClientRequest(user.getLogin(), user1));

    }


    public void registerEnterprise( RegisterEnterpriseRequest registerDto){
        String encryptPassword = new BCryptPasswordEncoder().encode(registerDto.password());

        User user = new User(registerDto.login(),encryptPassword, UserRole.ADMIN);
        User user1 = userRepository.save(user);

        enterpriseService.save(new EnterpriseRequest(registerDto.name(), registerDto.services(), user1));
    }
}
