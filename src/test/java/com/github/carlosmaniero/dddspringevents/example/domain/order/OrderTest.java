package com.github.carlosmaniero.dddspringevents.example.domain.order;

import com.github.carlosmaniero.dddspringevents.domain.DomainEventsPerformer;
import com.github.carlosmaniero.dddspringevents.example.infrastructure.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderTest {

    @Autowired
    private DomainEventsPerformer domainEventsPerformer;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void delivered_definesTheOrderAsDeliveredWhenTheDeliverEventIsSend() {
        OrderId id = new OrderId(1);
        Order order = new Order(id);

        orderRepository.save(order);

        Instant deliverTime = Instant.now();
        domainEventsPerformer.perform(new DeliveredDomainEvent(id, deliverTime));

        assertThat(order.isDelivered()).isTrue();
        assertThat(order.getDeliveredTimestamp()).isEqualTo(deliverTime);
    }
}
