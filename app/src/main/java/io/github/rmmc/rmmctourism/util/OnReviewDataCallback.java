package io.github.rmmc.rmmctourism.util;

import java.util.List;

public interface OnReviewDataCallback<T> {
    void onSuccess(List<T> reviews);

    void onFailure();
}
