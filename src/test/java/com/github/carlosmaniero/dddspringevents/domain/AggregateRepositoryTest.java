package com.github.carlosmaniero.dddspringevents.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AggregateRepositoryTest {
    @Test
    void isRepositoryOf_returnsTrueForTheAggregateRootClass() {
        AggregateRepository<TestDomainId, TestAggregateDomain> aggregateDomainRepository = new TestAggregateDomainRepository();

        assertThat(aggregateDomainRepository.isRepositoryOf(TestAggregateDomain.class))
            .isTrue();

        assertThat(aggregateDomainRepository.isRepositoryOf(TestAggregate2Domain.class))
                .isFalse();
    }

    private static class TestDomainId { }

    private static class TestAggregateDomain implements AggregateRoot<TestDomainId> {
        @Override
        public TestDomainId getId() {
            return null;
        }
    }

    private static class TestAggregate2Domain implements AggregateRoot<TestDomainId> {
        @Override
        public TestDomainId getId() {
            return null;
        }
    }

    private static class TestAggregateDomainRepository implements AggregateRepository<TestDomainId, TestAggregateDomain> {
        @Override
        public TestAggregateDomain forId(TestDomainId id) {
            return null;
        }
    }

}
