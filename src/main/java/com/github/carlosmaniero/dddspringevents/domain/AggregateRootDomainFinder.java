package com.github.carlosmaniero.dddspringevents.domain;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AggregateRootDomainFinder {
    private final ListableBeanFactory beanFactory;

    public AggregateRepository findRepository(Class<? extends AggregateRoot> aggregateDomainClass) {
        return beanFactory.getBeansOfType(AggregateRepository.class).values()
                .stream()
                .filter(aggregateRepository -> aggregateRepository.isRepositoryOf(aggregateDomainClass))
                .findFirst()
                .orElseThrow(() -> new RepositoryNotFoundException(aggregateDomainClass));
    }

    public static class RepositoryNotFoundException extends RuntimeException {
        public RepositoryNotFoundException(Class<? extends AggregateRoot> aggregateDomainClass) {
            super(String.format("There is no repository for %s", aggregateDomainClass.getTypeName()));
        }
    }
}
