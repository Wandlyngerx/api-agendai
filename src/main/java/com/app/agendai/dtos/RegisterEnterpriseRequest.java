package com.app.agendai.dtos;

import com.app.agendai.entities.ServiceEntity;
import com.app.agendai.entities.UserRole;

import javax.management.relation.Role;
import java.util.List;

public record RegisterEnterpriseRequest(String login, String password, String name, List<ServiceEntity> services) {
}
