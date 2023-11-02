package com.greatminds.ayni.management.interfaces.rest.resources;

import com.greatminds.ayni.management.domain.model.valueobjects.ProductId;

public record CropResource(Long id, String name, Boolean undergrowth, Boolean fertilize, Boolean oxygenate, Boolean line, Boolean hole, Long watered, Long pestCleaning, Long productId) {
}
