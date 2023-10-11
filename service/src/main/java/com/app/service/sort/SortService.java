package com.app.service.sort;

import java.util.Comparator;
import java.util.List;

public interface SortService<T> {
    List<T> sortBy(Comparator<T> comparator);
}
