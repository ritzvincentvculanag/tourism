package io.github.rmmc.rmmctourism.util;

import android.content.ContentResolver;

public interface ImageDataCallback {

    void onSuccess();
    void onFailure(Exception exception);
}
