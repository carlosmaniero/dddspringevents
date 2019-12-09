package com.github.carlosmaniero.dddspringevents.example.domain.order;

import com.github.carlosmaniero.dddspringevents.domain.AggregateRoot;
import com.github.carlosmaniero.dddspringevents.domain.DomainEventListener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class Order implements AggregateRoot<OrderId> {
    private final OrderId id;
    private final List<OrderItems> orderItems = new ArrayList<>();
    private boolean delivered = false;
    private Instant deliveredTimestamp;

    @DomainEventListener(DeliveredDomainEvent.class)
    public void delivered(DeliveredDomainEvent deliveredDomainEvent) {
        this.delivered = true;
        this.deliveredTimestamp = deliveredDomainEvent.getDeliverTime();
    }
}
