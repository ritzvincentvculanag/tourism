package io.github.rmmc.rmmctourism.util;

public interface DataCallBack<T> {

    void onDataLoaded(T t);

    void onDataNotAvailable(String error);
}
