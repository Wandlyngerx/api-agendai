package com.app.agendai.dtos;

import java.time.LocalTime;
import java.util.UUID;

public record ServiceRequest(String name, double price, LocalTime open, LocalTime closed) {
}
