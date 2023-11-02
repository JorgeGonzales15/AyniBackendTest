package com.greatminds.ayni.finance.interfaces.rest.resources;

import java.util.Date;

public record TransactionResource(
        Long Id, String costName, String description, String type, double price, Integer quantity
) {
}
