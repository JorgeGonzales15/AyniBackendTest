package com.greatminds.ayni.shopping.application.internal.queryservices;

import com.greatminds.ayni.shopping.domain.model.aggregates.Order;
import com.greatminds.ayni.shopping.domain.model.queries.GetAllOrdersQuery;
import com.greatminds.ayni.shopping.domain.model.queries.GetOrderByIdQuery;
import com.greatminds.ayni.shopping.domain.model.queries.GetOrderBySaleIdQuery;
import com.greatminds.ayni.shopping.domain.services.OrderQueryService;
import com.greatminds.ayni.shopping.infrastructure.persistence.jpa.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;
    public OrderQueryServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<Order> handle(GetOrderByIdQuery query) {
        return orderRepository.findById(query.orderId());
    }

    @Override
    public Optional<Order> handle(GetOrderBySaleIdQuery query) {
        return orderRepository.findById(query.saleId());
    }

    @Override
    public List<Order> handle(GetAllOrdersQuery query) {
        return orderRepository.findAll();
    }
}