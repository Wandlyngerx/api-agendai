package com.app.agendai.dtos;

import com.app.agendai.entities.ServiceEntity;
import com.app.agendai.entities.User;

import java.util.List;

public record EnterpriseRequest(String name, List<ServiceEntity> services, User user) {
}
