package io.github.rmmc.rmmctourism.repository;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.util.BatchUploadCallback;
import io.github.rmmc.rmmctourism.util.ImageDataCallback;
import io.github.rmmc.rmmctourism.util.OnImageLoadListener;

public class ImageRepository {

    FirebaseStorage storage;
    StorageReference storageRef;

    public ImageRepository() {
        this.storage = FirebaseStorage.getInstance();
        this.storageRef = storage.getReference();
    }

    public void uploadImageCover(Uri imageUri, String destinationId, ImageView imageView, ContentResolver contentResolver, ImageDataCallback imageDataCallback) {

        String filename = getFileNameAndExtension(imageUri, contentResolver);

        StorageReference destinationRef = storageRef.child("images/destination/" + destinationId + "/cover/" + filename);

        UploadTask uploadTask = destinationRef.putFile(imageUri);

        uploadTask.addOnSuccessListener(taskSnapshot -> {

            if (imageDataCallback != null) {
                imageDataCallback.onSuccess();
            }
        }).addOnFailureListener(e -> {
            e.printStackTrace();
            if (imageDataCallback != null) {
                imageDataCallback.onFailure(e);
            }
        });
    }

    public void batchUploadImages(List<Uri> imageUris, String destinationId, ContentResolver contentResolver, BatchUploadCallback callback) {

        final int totalImages = imageUris.size();
        final int[] uploadedCount = {0};
        final List<String> downloadUrls = new ArrayList<>();

        for (Uri imageUri : imageUris) {
            String fileName = getFileNameAndExtension(imageUri, contentResolver);

            StorageReference destinationRef = storageRef.child("images/destination/" + destinationId + "/gallery/" + fileName);

            UploadTask uploadTask = destinationRef.putFile(imageUri);

            uploadTask.addOnCompleteListener(task -> {
                uploadedCount[0]++;
                if (task.isSuccessful()) {

                    destinationRef.getDownloadUrl().addOnCompleteListener(uriTask -> {
                        if (uriTask.isSuccessful()) {
                            downloadUrls.add(uriTask.getResult().toString());
                        }
                        if (uploadedCount[0] == totalImages) {
                            if (callback != null) {
                                callback.onSuccess(downloadUrls);
                            }
                        }
                    });
                } else {
                    if (callback != null) {
                        callback.onFailure(task.getException());
                    }
                }
            });
        }
    }

    public String getFileNameAndExtension(Uri uri, ContentResolver contentResolver) {
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DISPLAY_NAME};
            cursor = contentResolver.query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
                String fileName = cursor.getString(columnIndex);
                if (fileName != null) {
                    int dotIndex = fileName.lastIndexOf(".");
                    if (dotIndex != -1) {
                        return fileName;
                    }
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public void loadUploadedImage(String destinationId, ImageView imageView) {
        // Construct the StorageReference with the gs:// URL
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference destinationRef = storage.getReferenceFromUrl("gs://tourismrmmc.appspot.com/images/destination/" + destinationId + "/cover/");


        destinationRef.listAll().addOnSuccessListener(listResult -> {
            if (!listResult.getItems().isEmpty()) {
                StorageReference imageRef = listResult.getItems().get(0);
                imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Picasso.get()
                                .load(String.valueOf(task.getResult()))
                                .placeholder(R.drawable.sample) // Placeholder image while loading
                                .error(R.drawable.sample) // Image to display in case of an error
                                .into(imageView);
                    }
                });


            }
        }).addOnFailureListener(e -> {
            Picasso.get().load(R.drawable.sample);
        });
    }

    public void loadGalleryImage(String destinationId, OnImageLoadListener<String> listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference destinationRef = storage.getReferenceFromUrl("gs://tourismrmmc.appspot.com/images/destination/" + destinationId + "/gallery/");

        destinationRef.listAll().addOnSuccessListener(listResult -> {
            List<String> imageUris = new ArrayList<>();

            for (StorageReference item : listResult.getItems()) {
                item.getDownloadUrl().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        imageUris.add(String.valueOf(task.getResult()));
                        Log.d(TAG, imageUris.size() + String.valueOf(task.getResult()));
                    } else {
                        if (listener != null) {
                            listener.onImageLoadFailure(task.getException());
                        }
                    }

                    // Check if all tasks are completed
                    if (imageUris.size() == listResult.getItems().size()) {
                        if (listener != null) {
                            listener.onImageLoadSuccess(imageUris);
                        }
                    }
                });
            }
        }).addOnFailureListener(e -> {
            if (listener != null) {
                listener.onImageLoadFailure(e);
            }
        });
    }

}
