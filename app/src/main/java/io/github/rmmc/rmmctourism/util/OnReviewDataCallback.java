package io.github.rmmc.rmmctourism.util;

import java.util.List;

import io.github.rmmc.rmmctourism.model.Review;

public interface OnReviewDataCallback<T> {
    void onSuccess(List<T> reviews);
    void onFailure();
}
