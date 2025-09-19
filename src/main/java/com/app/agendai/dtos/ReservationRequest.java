package com.app.agendai.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ReservationRequest(LocalDate date, LocalTime checkin, LocalTime checkout) {
}
