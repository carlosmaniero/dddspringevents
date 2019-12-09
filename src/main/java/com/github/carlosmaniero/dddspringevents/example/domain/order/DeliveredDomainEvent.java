package com.github.carlosmaniero.dddspringevents.example.domain.order;

import com.github.carlosmaniero.dddspringevents.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class DeliveredDomainEvent implements DomainEvent<OrderId, Order> {
    private final OrderId id;
    private final Instant deliverTime;
}
