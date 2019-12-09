package com.github.carlosmaniero.dddspringevents.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DomainEventTest {
    @Test
    void getAggregateRootClass() {
        DomainEvent<TestDomainId, TestAggregateDomain> domainEvent = new TestDomainEvent();
        assertThat(domainEvent.getAggregateRootClass()).isEqualTo(TestAggregateDomain.class);
    }

    private static class TestDomainId { }

    private static class TestAggregateDomain implements AggregateRoot<TestDomainId> {
        @Override
        public TestDomainId getId() {
            return null;
        }
    }

    private static class TestDomainEvent implements DomainEvent<TestDomainId, TestAggregateDomain> {
        @Override
        public TestDomainId getId() {
            return null;
        }
    }
}
