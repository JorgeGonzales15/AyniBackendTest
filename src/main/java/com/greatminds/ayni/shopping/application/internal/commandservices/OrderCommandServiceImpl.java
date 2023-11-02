package com.greatminds.ayni.shopping.application.internal.commandservices;

import com.greatminds.ayni.shopping.domain.model.commands.CreateOrderCommand;
import com.greatminds.ayni.shopping.domain.model.queries.GetSaleByIdQuery;
import com.greatminds.ayni.shopping.domain.services.SaleQueryService;
import com.greatminds.ayni.shopping.interfaces.rest.resources.UpdateOrderResource;
import com.greatminds.ayni.shopping.domain.model.aggregates.Order;
import com.greatminds.ayni.shopping.domain.services.OrderCommandService;
import com.greatminds.ayni.shopping.infrastructure.persistence.jpa.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;
    private final SaleQueryService saleQueryService;
    public OrderCommandServiceImpl(OrderRepository orderRepository, SaleQueryService saleQueryService) {
        this.orderRepository = orderRepository;
        this.saleQueryService = saleQueryService;
    }

    @Override
    public Long handle(CreateOrderCommand command) {
        var getSaleByIdQuery = new GetSaleByIdQuery(command.saleId());
        var sale = saleQueryService.handle(getSaleByIdQuery).orElseThrow();
        Date currentDate = new Date();
        var order = new Order(command.description(), command.totalPrice(), command.quantity(), command.paymentMethod(), command.status(), sale, command.orderedBy(), command.acceptedBy(), currentDate);
        order.updateDate(currentDate);
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public Long deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException("Order with ID " + orderId + " not found");
        }
        orderRepository.deleteById(orderId);
        return orderId;
    }

    @Override
    public Long updateOrder(Long orderId, UpdateOrderResource request) {
        return null;
    }

    /*@Override
    public Long updateOrder(Long orderId, UpdateOrderResource request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + orderId + " not found"));

        order.update(new Order(request.getDescription(), request.getTotalPrice(), request.getQuantity(), request.getPaymentMethod(), request.getStatus(), request.getSaleId(), request.getOrderedBy(), request.getAcceptedBy(), request.getOrderedDate())
        orderRepository.save(order);
        return order.getId();
    }*/
}