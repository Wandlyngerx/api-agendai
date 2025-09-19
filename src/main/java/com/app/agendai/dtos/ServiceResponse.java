package com.app.agendai.dtos;

import java.time.LocalTime;
import java.util.UUID;

public record ServiceResponse(UUID id, String name, double price, LocalTime open, LocalTime closed) {
}
