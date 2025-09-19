package com.app.agendai.controller;

import com.app.agendai.dtos.ClientRequest;
import com.app.agendai.dtos.ClientResponse;
import com.app.agendai.entities.Client;
import com.app.agendai.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendai/client")
//@CrossOrigin("*")
public class ClientController {


    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findClientById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.findById(id));
    }
}
