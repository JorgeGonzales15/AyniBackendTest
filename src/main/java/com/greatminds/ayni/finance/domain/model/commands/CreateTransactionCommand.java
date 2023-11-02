package com.greatminds.ayni.finance.domain.model.commands;

public record CreateTransactionCommand(String costName, String description, String type, double price, Integer quantity) {

}

