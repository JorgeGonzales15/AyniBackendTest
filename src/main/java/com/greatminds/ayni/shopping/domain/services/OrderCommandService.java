package com.greatminds.ayni.shopping.domain.services;


import com.greatminds.ayni.shopping.domain.model.commands.CreateOrderCommand;
import com.greatminds.ayni.shopping.interfaces.rest.resources.UpdateOrderResource;

public interface OrderCommandService {

    Long handle(CreateOrderCommand command);

    Long deleteOrder(Long orderId);

    Long updateOrder(Long orderId, UpdateOrderResource request);
}