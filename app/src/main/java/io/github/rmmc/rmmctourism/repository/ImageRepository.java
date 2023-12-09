package io.github.rmmc.rmmctourism.repository;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.UUID;

public class ImageRepository {

    FirebaseStorage storage;
    StorageReference storageRef;

    public ImageRepository() {
        this.storage = FirebaseStorage.getInstance();
        this.storageRef = storage.getReference();
    }

    public void uploadImage(Uri imageUri, String destinationId, ImageView imageView) {

        if (!isValidFileExtension(imageUri)) {
            Toast.makeText(imageView.getContext(), "Invalid file format. Please choose a PNG or JPG image.", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = UUID.randomUUID().toString() + getFileExtension(imageUri);

        StorageReference destinationRef = storageRef.child("images/" + destinationId + "/");

        StorageReference imageRef = destinationRef.child(fileName);

        UploadTask uploadTask = imageRef.putFile(imageUri);

        uploadTask.addOnSuccessListener(taskSnapshot -> {

            loadUploadedImage(destinationId, imageView);
        }).addOnFailureListener(e -> {

            e.printStackTrace();
        });
    }

    private boolean isValidFileExtension(Uri imageUri) {
        String fileExtension = getFileExtension(imageUri);
        return fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("jpg");
    }

    private String getFileExtension(Uri uri) {
        String extension = null;
        String uriString = uri.toString();
        int lastDot = uriString.lastIndexOf('.');
        if (lastDot > 0) {
            extension = uriString.substring(lastDot + 1);
        }
        return extension;
    }

    private void loadUploadedImage(String destinationId, ImageView imageView) {
        StorageReference destinationRef = storageRef.child("images/" + destinationId + "/");

        destinationRef.listAll()
                .addOnSuccessListener(listResult -> {
                    if (!listResult.getItems().isEmpty()) {
                        StorageReference imageRef = listResult.getItems().get(0);
                        Glide.with(imageView.getContext())
                                .load(imageRef)
                                .apply(new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)) // Disable caching for development
                                .into(imageView);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that occurred while listing items
                    e.printStackTrace();
                });
    }
}
