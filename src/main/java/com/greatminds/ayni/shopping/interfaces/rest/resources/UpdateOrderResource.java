package com.greatminds.ayni.shopping.interfaces.rest.resources;

import java.util.Date;

public record UpdateOrderResource(String description, Double totalPrice, Integer quantity, String paymentMethod, String status, Long saleId, Long orderedBy, Long acceptedBy, Date orderedDate) {
}