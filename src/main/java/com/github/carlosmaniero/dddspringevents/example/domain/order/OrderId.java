package com.github.carlosmaniero.dddspringevents.example.domain.order;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class OrderId {
    private final int id;
}
