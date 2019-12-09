package com.github.carlosmaniero.dddspringevents.example.infrastructure;

import com.github.carlosmaniero.dddspringevents.domain.AggregateRepository;
import com.github.carlosmaniero.dddspringevents.example.domain.order.Order;
import com.github.carlosmaniero.dddspringevents.example.domain.order.OrderId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository implements AggregateRepository<OrderId, Order> {
    private final List<Order> orders = new ArrayList<>();

    @Override
    public Order forId(OrderId id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst().orElse(null);
    }

    public void save(Order order) {
        this.orders.add(order);
    }
}
