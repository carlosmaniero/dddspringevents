package com.github.carlosmaniero.dddspringevents.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class AggregateRootTest {
    @MockBean
    private DomainEventPublisher domainEventPublisher;

    @Test
    void domainEventPublisherIsCalledWhenAMethodAnnotatedWithPublishEventIsCalled() {
        TestDomainId id = new TestDomainId(1);
        TestAggregateDomain testAggregateDomain = new TestAggregateDomain(id);
        testAggregateDomain.myMethod();

        verify(domainEventPublisher).publish(new TestDomainEvent(id));
    }

    @AllArgsConstructor
    private static class TestDomainId {
        private final int id;
    }

    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class TestDomainEvent implements DomainEvent<TestDomainId, TestAggregateDomain> {
        private final TestDomainId id;
    }

    @AllArgsConstructor
    @Getter
    private static class TestAggregateDomain implements AggregateRoot<TestDomainId> {
        private final TestDomainId id;

        public void myMethod() {
            DomainEventPublisherContext.domainPublisher()
                    .publish(new TestDomainEvent(id));
        }
    }
}
