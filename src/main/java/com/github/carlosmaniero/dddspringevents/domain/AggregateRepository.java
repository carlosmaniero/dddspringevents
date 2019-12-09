package com.github.carlosmaniero.dddspringevents.domain;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface AggregateRepository<ID_TYPE, AGGREGATE_TYPE extends AggregateRoot<ID_TYPE>> {
    AGGREGATE_TYPE forId(ID_TYPE id);

    default boolean isRepositoryOf(Class<? extends AggregateRoot> aggregateDomainClass) {
        Type genericInterface = this.getClass().getGenericInterfaces()[0];
        try {
            Type[] actualTypeArguments = ((ParameterizedType) genericInterface).getActualTypeArguments();
            Type actualTypeArgument = actualTypeArguments[1];
            return Class.forName(actualTypeArgument.getTypeName()).equals(aggregateDomainClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
