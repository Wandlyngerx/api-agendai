package com.app.agendai.dtos;

import com.app.agendai.entities.User;

import java.util.UUID;

public record ClientRequest(String name , User user) {
}
