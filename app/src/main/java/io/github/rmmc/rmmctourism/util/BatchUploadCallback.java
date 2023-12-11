package io.github.rmmc.rmmctourism.util;

import java.util.List;

public interface BatchUploadCallback {
    void onSuccess(List<String> downloadUrls);
    void onFailure(Exception exception);
}