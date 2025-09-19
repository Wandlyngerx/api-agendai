package com.app.agendai.controller;

import com.app.agendai.dtos.EnterpriseRequest;
import com.app.agendai.dtos.EnterpriseResponse;
import com.app.agendai.entities.Enterprise;
import com.app.agendai.services.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendai/enterprise")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @GetMapping()
    public ResponseEntity<List<EnterpriseResponse>> findEnterprise(){
        return ResponseEntity.status(HttpStatus.OK).body(enterpriseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnterpriseResponse> findEnterpriseById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(enterpriseService.findById(id));
    }
}
