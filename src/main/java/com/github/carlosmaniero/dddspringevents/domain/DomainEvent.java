package com.github.carlosmaniero.dddspringevents.domain;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface DomainEvent<ID_TYPE, AGGREGATE_TYPE extends AggregateRoot<ID_TYPE>> {
    ID_TYPE getId();

    default Class<AGGREGATE_TYPE> getAggregateRootClass() {
        Type genericInterface = this.getClass().getGenericInterfaces()[0];
        try {
            Type[] actualTypeArguments = ((ParameterizedType) genericInterface).getActualTypeArguments();
            Type actualTypeArgument = actualTypeArguments[1];
            return (Class<AGGREGATE_TYPE>) Class.forName(actualTypeArgument.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
