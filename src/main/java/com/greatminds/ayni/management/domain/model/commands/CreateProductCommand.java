package com.greatminds.ayni.management.domain.model.commands;

public record CreateProductCommand(String name, String description, String distance, String depth, String weather, String groundType, String season, String imageUrl) {
}
