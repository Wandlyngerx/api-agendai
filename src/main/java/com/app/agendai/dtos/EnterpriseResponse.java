package com.app.agendai.dtos;

import java.util.List;
import java.util.UUID;

public record EnterpriseResponse(UUID id, String name, List<UUID> servicesId) {
}
