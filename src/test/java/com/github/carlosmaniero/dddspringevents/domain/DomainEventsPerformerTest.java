package com.github.carlosmaniero.dddspringevents.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@SpringBootTest
class DomainEventsPerformerTest {
    @Autowired
    private DomainEventsPerformer domainEventsPerformer;

    @MockBean
    private AggregateRepository<TestDomainId, TestAggregateDomain> repository;

    @Test
    void perform_publishesDomainEvent() {
        TestDomainId id = new TestDomainId(1);
        TestDomainEvent event = new TestDomainEvent(id);

        TestAggregateDomain aggregate = new TestAggregateDomain(id, event);

        when(repository.isRepositoryOf(TestAggregateDomain.class)).thenReturn(true);
        when(repository.forId(id)).thenReturn(aggregate);

        domainEventsPerformer.perform(event);

        assertThat(aggregate.isTestEventCalledWithTheExpectedEvent()).isTrue();
    }

    @Test
    void perform_publishesDomainEventForListenersWithoutAttributes() {
        TestDomainId id = new TestDomainId(1);
        TestDomainEvent event = new TestDomainEvent(id);

        TestAggregateDomain aggregate = new TestAggregateDomain(id, event);

        when(repository.isRepositoryOf(TestAggregateDomain.class)).thenReturn(true);
        when(repository.forId(id)).thenReturn(aggregate);

        domainEventsPerformer.perform(event);

        assertThat(aggregate.isTestEventCalledWithNoAttributes()).isTrue();
    }

    @Test
    void perform_doesNotCallsAnyMethodIfThereIsNoListeners() {
        TestDomainId id = new TestDomainId(1);
        TestDomainEventWithNoListeners event = new TestDomainEventWithNoListeners(id);

        TestAggregateDomain aggregate = new TestAggregateDomain(id, event);

        when(repository.isRepositoryOf(TestAggregateDomain.class)).thenReturn(true);
        when(repository.forId(id)).thenReturn(aggregate);

        domainEventsPerformer.perform(event);

        assertThat(aggregate.isTestEventCalledWithTheExpectedEvent()).isFalse();
    }

    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    private static class TestDomainId {
        private final int id;
    }

    @AllArgsConstructor
    @Getter
    private static class TestDomainEvent implements DomainEvent<TestDomainId, TestAggregateDomain> {
        private final TestDomainId id;
    }

    @AllArgsConstructor
    @Getter
    private static class TestDomainEventWithNoListeners implements DomainEvent<TestDomainId, TestAggregateDomain> {
        private final TestDomainId id;
    }

    @RequiredArgsConstructor
    @Getter
    private static class TestAggregateDomain implements AggregateRoot<TestDomainId> {
        private final TestDomainId id;
        private final DomainEvent expectedDomainEvent;
        private boolean testEventCalledWithTheExpectedEvent = false;
        private boolean testEventCalledWithNoAttributes = false;

        @DomainEventListener(TestDomainEvent.class)
        void testEvent(TestDomainEvent testDomainEvent) {
            this.testEventCalledWithTheExpectedEvent = true;
            assertThat(testDomainEvent).isSameAs(this.expectedDomainEvent);
        }

        @DomainEventListener(TestDomainEvent.class)
        void testEventWithNoAttributes() {
            this.testEventCalledWithNoAttributes = true;
        }
    }
}
