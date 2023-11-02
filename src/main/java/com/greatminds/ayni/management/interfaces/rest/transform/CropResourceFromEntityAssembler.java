package com.greatminds.ayni.management.interfaces.rest.transform;

import com.greatminds.ayni.management.domain.model.aggregates.Crop;
import com.greatminds.ayni.management.interfaces.rest.resources.CropResource;

public class CropResourceFromEntityAssembler {
    public static CropResource toResourceFromEntity(Crop entity){
        return new CropResource(
                entity.getId(),
                entity.getName(),
                entity.getUndergrowth(),
                entity.getFertilize(),
                entity.getOxygenate(),
                entity.getLine(),
                entity.getHole(),
                entity.getWatered(),
                entity.getPestCleaning(),
                entity.getProduct().getId()
        );
    }
}
