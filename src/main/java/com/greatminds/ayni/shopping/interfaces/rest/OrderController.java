package com.greatminds.ayni.shopping.interfaces.rest;

import com.greatminds.ayni.shopping.domain.model.aggregates.Order;
import com.greatminds.ayni.shopping.domain.model.commands.CreateOrderCommand;
import com.greatminds.ayni.shopping.domain.model.queries.GetAllOrdersQuery;
import com.greatminds.ayni.shopping.domain.model.queries.GetOrderByIdQuery;
import com.greatminds.ayni.shopping.domain.services.OrderCommandService;
import com.greatminds.ayni.shopping.domain.services.OrderQueryService;
import com.greatminds.ayni.shopping.interfaces.rest.resources.CreateOrderResource;
import com.greatminds.ayni.shopping.interfaces.rest.resources.OrderResource;
import com.greatminds.ayni.shopping.interfaces.rest.resources.UpdateOrderResource;
import com.greatminds.ayni.shopping.interfaces.rest.transform.CreateOrderCommandFromResourceAssembler;
import com.greatminds.ayni.shopping.interfaces.rest.transform.OrderResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Orders", description = "Orders Management Endpoints")
public class OrderController {
    private final OrderQueryService orderQueryService;
    private final OrderCommandService orderCommandService;

    public OrderController(OrderQueryService orderQueryService, OrderCommandService orderCommandService) {
        this.orderQueryService = orderQueryService;
        this.orderCommandService = orderCommandService;
    }

    @PostMapping
    public ResponseEntity<OrderResource> createOrder(@RequestBody CreateOrderResource resource){
        var createOrderCommand = CreateOrderCommandFromResourceAssembler.toCommandFromResource(resource);

        var orderId = orderCommandService.handle(createOrderCommand);
        if(orderId == 0L){
            return ResponseEntity.badRequest().build();
        }

        var getOrderIdByQuery = new GetOrderByIdQuery(orderId);
        var order = orderQueryService.handle(getOrderIdByQuery);

        if(order.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        var orderResource = OrderResourceFromEntityAssembler.toResourceFromEntity(order.get());
        return new ResponseEntity<>(orderResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResource>> getAllOrders() {
        var getAllOrdersQuery = new GetAllOrdersQuery();
        var orders = orderQueryService.handle(getAllOrdersQuery);
        var ordersResources = orders.stream().map(OrderResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(ordersResources);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResource> getOrderById(@PathVariable Long orderId) {
        var getOrderIdByQuery = new GetOrderByIdQuery(orderId);
        var order = orderQueryService.handle(getOrderIdByQuery);
        if (order.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var orderResource = OrderResourceFromEntityAssembler.toResourceFromEntity(order.get());
        return ResponseEntity.ok(orderResource);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<OrderResource> updateOrder(@PathVariable Long orderId, @RequestBody UpdateOrderResource resource) {
        try {
            Long updatedOrderId = orderCommandService.updateOrder(orderId, resource);
            Order updatedOrder = orderQueryService.handle(new GetOrderByIdQuery(updatedOrderId))
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));
            return ResponseEntity.ok(OrderResourceFromEntityAssembler.toResourceFromEntity(updatedOrder));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        var getOrderByIdQuery = new GetOrderByIdQuery(orderId);
        var existingOrder = orderQueryService.handle(getOrderByIdQuery);

        if (existingOrder.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        orderCommandService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

}
