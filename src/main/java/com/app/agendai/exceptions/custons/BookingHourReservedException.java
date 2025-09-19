package com.app.agendai.exceptions.custons;

public class BookingHourReservedException extends RuntimeException{

    public  BookingHourReservedException(){
        super("esse horario ja esta reservado");
    }
}
