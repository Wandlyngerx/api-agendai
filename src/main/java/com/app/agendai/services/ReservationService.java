package com.app.agendai.services;

import com.app.agendai.dtos.ReservationRequest;
import com.app.agendai.entities.Reservation;
import com.app.agendai.entities.Client;
import com.app.agendai.entities.ServiceEntity;
import com.app.agendai.entities.User;
import com.app.agendai.exceptions.custons.BookingHourInvalidException;
import com.app.agendai.exceptions.custons.BookingHourReservedException;
import com.app.agendai.exceptions.custons.NotFoundException;
import com.app.agendai.repositories.ReservationRepository;
import com.app.agendai.repositories.ClientRepository;
import com.app.agendai.repositories.ServiceRepository;
import com.app.agendai.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    public com.app.agendai.dtos.ReservationResponse save(UUID id, ReservationRequest reservationRequest, UserDetails userDetails) {

        ServiceEntity serviceEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("servcio "+ id +" nao encontrado" ));


        if (reservationRequest.checkin().isBefore(serviceEntity.getOpen()) ||
                reservationRequest.checkin().isAfter(serviceEntity.getClosed()) ||
                reservationRequest.checkout().isAfter(serviceEntity.getClosed())) {
            throw new BookingHourInvalidException();
        }


        List<Reservation> reservations = reservationRepository.findByDateAndServiceEntityEnterpriseId(reservationRequest.date(), serviceEntity.getEnterprise().getId());


        for (Reservation reservation : reservations) {
            boolean naoConflita = reservationRequest.checkout().compareTo(reservation.getCheckin()) <= 0
                    || reservationRequest.checkin().compareTo(reservation.getCheckout()) >= 0;


            if (!naoConflita) {
                throw new BookingHourReservedException();
            }
        }

        UserDetails userDetails1 = userRepository.findByLogin(userDetails.getUsername());
        Client client = clientRepository.findByUser(userDetails1).orElseThrow(()-> new NotFoundException("nao encontrado"));


        Reservation reservation = new Reservation();
        reservation.setDate(reservationRequest.date());
        reservation.setCheckin(reservationRequest.checkin());
        reservation.setCheckout(reservationRequest.checkout());
        reservation.setClient(client);
        reservation.setServiceEntity(serviceEntity);

        reservationRepository.save(reservation);
        return new com.app.agendai.dtos.ReservationResponse(reservation.getId(), reservation.getDate(), reservation.getCheckin(), reservation.getCheckout(), reservation.getClient().getId(), reservation.getServiceEntity().getId());


    }

public List<com.app.agendai.dtos.ReservationResponse> findByClient(UUID id){
        boolean exist = clientRepository.existsById(id);
        if (!exist){
            throw new NotFoundException("nao foi encontrado um cliente com id " + id );
        }

        List<Reservation> reservations = reservationRepository.findByClientId(id);

        return bookingResponseList(reservations);
}


    public List<com.app.agendai.dtos.ReservationResponse> findAll(){
        List<Reservation> reservations = reservationRepository.findAll();

        return bookingResponseList(reservations);
    }

    public List<com.app.agendai.dtos.ReservationResponse> findByService(UUID id){
        boolean exist = serviceRepository.existsById(id);
        if (!exist){
            throw new NotFoundException("servico com id " + id+ "nao encontrado");
        }


        List<Reservation> reservations = reservationRepository.findByServiceEntityId(id);

        return bookingResponseList(reservations);
    }



    private List<com.app.agendai.dtos.ReservationResponse> bookingResponseList(List<Reservation> reservations) {
        return reservations.stream()
                .map(booking -> new com.app.agendai.dtos.ReservationResponse(
                        booking.getId(),
                        booking.getDate(),
                        booking.getCheckin(),
                        booking.getCheckout(),
                        booking.getClient().getId(),
                        booking.getServiceEntity().getId()
                ))
                .collect(Collectors.toList());
    }

}
