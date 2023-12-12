package io.github.rmmc.rmmctourism.util;

import java.util.List;

import io.github.rmmc.rmmctourism.model.Favorite;

public interface OnFavoriteDataCallback {
    void onSuccess(List<Favorite> list);
    void onFailure(Exception exception);
}
