package com.app.agendai.controller;

import com.app.agendai.dtos.ServiceRequest;
import com.app.agendai.dtos.ServiceResponse;
import com.app.agendai.entities.ServiceEntity;
import com.app.agendai.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendai/service")
public class ServiceController {

    @Autowired
    private ServiceService service;

    @PostMapping
    public ResponseEntity<ServiceResponse> saveService(@RequestBody ServiceRequest serviceRequest, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(serviceRequest, userDetails));
    }

    @GetMapping("/enterprise/{id}")
    public ResponseEntity<List<ServiceResponse>>  findServiceByEnterprise(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(service.findByEnterpriseId(id));
    }
    @GetMapping("/service/{id}")
    public ResponseEntity<ServiceResponse>  findServiceById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable UUID id, @RequestBody ServiceRequest serviceRequest, @AuthenticationPrincipal UserDetails userDetails){

        return service.atualizar(id, serviceRequest, userDetails);
    }
}
