package com.greatminds.ayni.management.interfaces.rest.resources;

public record CreateProductResource(String name, String description, String distance, String depth, String weather, String groundType, String season, String imageUrl) {

}
