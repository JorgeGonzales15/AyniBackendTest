package com.greatminds.ayni.management.interfaces.rest.transform;

import com.greatminds.ayni.management.domain.model.commands.CreateProductCommand;
import com.greatminds.ayni.management.interfaces.rest.resources.CreateProductResource;

public class CreateProductCommandFromResourceAssembler {
    public static CreateProductCommand toCommandFromResource(CreateProductResource resource) {
        return new CreateProductCommand(resource.name(), resource.description(), resource.distance(), resource.depth(), resource.weather(), resource.groundType(), resource.season(), resource.imageUrl());
    }
}
