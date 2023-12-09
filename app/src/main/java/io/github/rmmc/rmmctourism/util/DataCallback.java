package io.github.rmmc.rmmctourism.util;

import java.util.List;

public interface DataCallback<T> {
    void onDataLoaded(List<T> t);

    void onDataNotAvailable();
}

