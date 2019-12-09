package com.github.carlosmaniero.dddspringevents.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
@AllArgsConstructor
public class DomainEventsPerformer {
    private final AggregateRootDomainFinder aggregateRootDomainFinder;

    public void perform(DomainEvent domainEvent) {
        AggregateRepository aggregateRepository = aggregateRootDomainFinder
                .findRepository(domainEvent.getAggregateRootClass());

        AggregateRoot aggregateRoot = aggregateRepository.forId(domainEvent.getId());

        for (Method method : domainEvent.getAggregateRootClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(DomainEventListener.class)) {
                method.setAccessible(true);
                try {
                    if (method.getParameterCount() == 1) {
                        Class<?> parameterType = method.getParameterTypes()[0];
                        if (parameterType.equals(domainEvent.getClass())) {
                            method.invoke(aggregateRoot, domainEvent);
                        }
                    } else {
                        method.invoke(aggregateRoot);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
