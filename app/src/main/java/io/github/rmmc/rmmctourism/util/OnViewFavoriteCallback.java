package io.github.rmmc.rmmctourism.util;

import io.github.rmmc.rmmctourism.model.Favorite;

public interface OnViewFavoriteCallback {
    void onSuccess(Favorite favorite);
    void onFailure();
}
