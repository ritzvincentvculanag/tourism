package io.github.rmmc.rmmctourism.util;

import java.util.List;

public interface DestinationDataCallback<T> {
    void onDataLoaded(List<T> t);

    void onDataNotAvailable();
}

