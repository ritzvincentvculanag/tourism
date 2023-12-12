package io.github.rmmc.rmmctourism.util;

import android.net.Uri;

import java.util.List;

public interface OnImageLoadListener<T> {
    void onImageLoadSuccess(List<T> imageUris);
    void onImageLoadFailure(Exception e);
}
