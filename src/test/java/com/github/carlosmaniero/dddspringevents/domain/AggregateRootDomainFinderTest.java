package com.github.carlosmaniero.dddspringevents.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
class AggregateRootDomainFinderTest {

    @Autowired
    private AggregateRootDomainFinder aggregateRootDomainFinder;

    @MockBean
    private AggregateRepository<TestDomainId, TestAggregateDomain> repository;

    @Test
    void repositoryInstance_returnsTheRepositoryInstanceForAnAggregate() {
        when(repository.isRepositoryOf(TestAggregateDomain.class))
            .thenReturn(true);

        AggregateRepository repository = aggregateRootDomainFinder.findRepository(TestAggregateDomain.class);
        assertThat(repository).isSameAs(this.repository);
    }

    @Test
    void repositoryInstance_throwsExceptionWhenThereIsNoRepository() {
        when(repository.isRepositoryOf(TestAggregateDomain.class))
                .thenReturn(false);

        assertThatThrownBy(() -> aggregateRootDomainFinder.findRepository(TestAggregateDomain.class))
                .isInstanceOf(AggregateRootDomainFinder.RepositoryNotFoundException.class);
    }

    private static class TestDomainId { }

    private static class TestAggregateDomain implements AggregateRoot<TestDomainId> {
        @Override
        public TestDomainId getId() {
            return null;
        }
    }
}
