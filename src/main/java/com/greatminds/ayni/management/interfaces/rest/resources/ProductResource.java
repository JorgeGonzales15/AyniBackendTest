package com.greatminds.ayni.management.interfaces.rest.resources;

public record ProductResource(Long id, String name, String description, String distance, String depth, String weather, String groundType, String season, String imageUrl) {
}
