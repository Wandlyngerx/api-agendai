package com.app.agendai.controller;


import com.app.agendai.dtos.ReservationRequest;
import com.app.agendai.dtos.ReservationResponse;
import com.app.agendai.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendai/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;


    @PostMapping("/{id}")
    public ResponseEntity<ReservationResponse> saveBooking (@PathVariable UUID id, @RequestBody ReservationRequest reservationRequest, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.save(id,reservationRequest, userDetails));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ReservationResponse>> findBookingByClient(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.findByClient(id));
    }
    @GetMapping("/service/{id}")
    public ResponseEntity<List<ReservationResponse>> findBookingByServcie(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.findByService(id));
}
}
