package com.app.agendai.exceptions;

import com.app.agendai.exceptions.custons.BookingHourInvalidException;
import com.app.agendai.exceptions.custons.BookingHourReservedException;
import com.app.agendai.exceptions.custons.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookingHourReservedException.class)
    private ResponseEntity<ExceptionMessage> bookingHourReserved(BookingHourReservedException e)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionMessage(HttpStatus.CONFLICT, "horario ja reservado"));
    }


    @ExceptionHandler(BookingHourInvalidException.class)
    private ResponseEntity<ExceptionMessage> bookingHourInvalid(BookingHourInvalidException e){
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ExceptionMessage(HttpStatus.BAD_GATEWAY, "reserva fora do horario de atendimento"));
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ExceptionMessage> notFound(NotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionMessage(HttpStatus.NOT_FOUND, e.getMessage()));
    }

}
