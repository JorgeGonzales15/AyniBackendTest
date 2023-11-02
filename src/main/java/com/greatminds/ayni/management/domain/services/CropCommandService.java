package com.greatminds.ayni.management.domain.services;

import com.greatminds.ayni.management.domain.model.commands.CreateCropCommand;

public interface CropCommandService {
    Long handle(CreateCropCommand command);
}
