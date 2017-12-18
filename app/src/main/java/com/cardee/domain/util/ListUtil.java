package com.cardee.domain.util;


import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    public static <T, V> List<T> map(List<V> list, Mapper<V, T> mapper) {
        if (list == null) {
            return null;
        }
        List<T> mapped = new ArrayList<>();
        for (V item : list) {
            mapped.add(mapper.map(item));
        }
        return mapped;
    }
}
