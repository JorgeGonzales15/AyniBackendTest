package com.greatminds.ayni.management.domain.services;

import com.greatminds.ayni.management.domain.model.commands.CreateProductCommand;

public interface ProductCommandService {
    Long handle(CreateProductCommand command);
}
