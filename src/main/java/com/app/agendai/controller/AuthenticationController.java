package com.app.agendai.controller;

import com.app.agendai.dtos.*;
import com.app.agendai.infra.security.TokenService;
import com.app.agendai.repositories.UserRepository;
import com.app.agendai.services.AuthenticationService;
import com.app.agendai.services.ClientService;
import com.app.agendai.services.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendai/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest data) {
        String token = this.authenticationService.login(data);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register/client")
    public ResponseEntity<?> registerClient(@RequestBody RegisterClientRequest data) {
        authenticationService.registerClient(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/enterprise")
    public ResponseEntity<?> registerEnterprise(@RequestBody RegisterEnterpriseRequest data) {

        authenticationService.registerEnterprise(data);

        return ResponseEntity.ok().build();
    }
}