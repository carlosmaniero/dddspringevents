package com.github.carlosmaniero.dddspringevents.domain;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class DomainEventPublisherContext implements ApplicationContextAware {
    private static DomainEventPublisher domainEventPublisher;

    public static DomainEventPublisher domainPublisher() {
        return domainEventPublisher;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        domainEventPublisher = applicationContext.getBean(DomainEventPublisher.class);
    }
}
