package com.greatminds.ayni.finance.interfaces.rest.resources;
public record UpdateTransactionResource(
        String costName, String description, String type, double price, Integer quantity
) {

}
