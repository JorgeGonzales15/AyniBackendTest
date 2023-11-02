package com.greatminds.ayni.management.domain.model.commands;

import com.greatminds.ayni.management.domain.model.valueobjects.ProductId;

public record CreateCropCommand(String name, Boolean undergrowth, Boolean fertilize, Boolean oxygenate, Boolean line, Boolean hole, Long watered, Long pestCleaning, Long productId) {
}
