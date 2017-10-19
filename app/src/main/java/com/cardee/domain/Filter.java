package com.cardee.domain;


import java.util.List;

public interface Filter<T> {

    List<T> apply(List<T> list);

}
