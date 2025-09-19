package com.app.agendai.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ReservationResponse(UUID id, LocalDate date, LocalTime checkin, LocalTime checkout, UUID clientId, UUID serviceId) {
}
