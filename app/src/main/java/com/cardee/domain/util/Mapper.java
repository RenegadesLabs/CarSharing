package com.cardee.domain.util;

public interface Mapper<V, T> {

    T map(V input);
}
