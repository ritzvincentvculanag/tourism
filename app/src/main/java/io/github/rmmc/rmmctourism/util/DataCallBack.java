package io.github.rmmc.rmmctourism.util;

import java.util.List;

public interface DataCallBack<T>{

    void onDataLoaded(T t);

    void onDataNotAvailable(String error);
}
