package com.cardee.domain.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtil {

    public static <T, V> List<T> asList(V[] array, Mapper<V, T> mapper) {
        if (array == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (V item : array) {
            list.add(mapper.map(item));
        }
        return list;
    }
}
