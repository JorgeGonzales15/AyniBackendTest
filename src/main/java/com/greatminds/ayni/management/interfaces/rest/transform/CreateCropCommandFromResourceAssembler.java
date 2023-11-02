package com.greatminds.ayni.management.interfaces.rest.transform;

import com.greatminds.ayni.management.domain.model.commands.CreateCropCommand;
import com.greatminds.ayni.management.interfaces.rest.resources.CreateCropResource;

public class CreateCropCommandFromResourceAssembler {
    public static CreateCropCommand toCommandFromResource(CreateCropResource resource){
        return new CreateCropCommand(resource.name(),resource.undergrowth(),resource.fertilize(),resource.oxygenate(),resource.line(),resource.hole(),resource.watered(),resource.pestCleaning(),resource.productId());
    }
}
