package io.github.rmmc.rmmctourism.util;

import java.util.List;

import io.github.rmmc.rmmctourism.model.ImageGallery;

public interface BatchUploadCallback {
    void onSuccess(List<ImageGallery> downloadUrls);
    void onFailure(Exception exception);
}