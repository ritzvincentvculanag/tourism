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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.model.ImageGallery;
import io.github.rmmc.rmmctourism.util.BatchUploadCallback;
import io.github.rmmc.rmmctourism.util.ImageDataCallback;
import io.github.rmmc.rmmctourism.util.OnDeleteImageCallback;
import io.github.rmmc.rmmctourism.util.OnImageLoadListener;
import io.github.rmmc.rmmctourism.util.OnLoadCover;

public class ImageRepository {

    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseFirestore instance;

    // Constructor
    public ImageRepository() {
        this.storage = FirebaseStorage.getInstance();
        this.storageRef = storage.getReference();
        this.instance = FirebaseFirestore.getInstance();
    }

    // Method to upload a cover image
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

    // Method to update the cover image
    public void updateCoverImage(Uri newCover, Uri oldCover, String destinationId, ContentResolver contentResolver, ImageDataCallback imageDataCallback) {

        if (newCover != null) {
            String filename = getFileNameAndExtension(newCover, contentResolver);
            StorageReference storageRef = storage.getReference();
            String oldCoverPath = oldCover.toString();
            Log.d(TAG, oldCoverPath);
            StorageReference desertRef = FirebaseStorage.getInstance().getReferenceFromUrl(oldCoverPath);
            desertRef.delete().addOnSuccessListener(aVoid -> {
                StorageReference destinationRef = storageRef.child("images/destination/" + destinationId + "/cover/" + filename);

                UploadTask uploadTask = destinationRef.putFile(newCover);

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
            }).addOnFailureListener(exception -> {
                // Handle the case where deletion of oldCover fails
                exception.printStackTrace();
            });
        }
        if (imageDataCallback != null) {
            imageDataCallback.onSuccess();
        }

    }

    // Method to batch upload images
    public void batchUploadImages(List<Uri> imageUris, String destinationId, ContentResolver contentResolver, BatchUploadCallback callback) {
        final int totalImages = imageUris.size();
        final int[] uploadedCount = {0};
        final List<ImageGallery> downloadUrls = new ArrayList<>();

        for (Uri imageUri : imageUris) {
            // Check if the Uri is from Firebase Storage
            if (isFirebaseStorageUri(imageUri)) {
                // Ignore Firebase Storage URIs
                uploadedCount[0]++;
                if (uploadedCount[0] == totalImages) {
                    if (callback != null) {
                        callback.onSuccess(downloadUrls);
                    }
                }
                continue;
            }

            String fileName = getFileNameAndExtension(imageUri, contentResolver);

            StorageReference destinationRef = storageRef.child("images/destination/" + destinationId + "/gallery/" + fileName);

            UploadTask uploadTask = destinationRef.putFile(imageUri);

            uploadTask.addOnCompleteListener(task -> {
                uploadedCount[0]++;
                if (task.isSuccessful()) {
                    destinationRef.getDownloadUrl().addOnCompleteListener(uriTask -> {
                        if (uriTask.isSuccessful()) {
                            downloadUrls.add(new ImageGallery(destinationId, uriTask.getResult().toString()));
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

    // Helper method to check if a Uri is from Firebase Storage
    private boolean isFirebaseStorageUri(Uri uri) {
        // Check if the Uri scheme is "gs" or "https" (typical for Firebase Storage URIs)
        return "gs".equals(uri.getScheme()) || "https".equals(uri.getScheme());
    }

    // Method to upload a batch of images
    public void uploadBatch(List<ImageGallery> imageGalleries) {
        WriteBatch batch = instance.batch();

        for (ImageGallery data : imageGalleries) {
            instance.collection(ImageGallery.collectionName)
                    .whereEqualTo("destinationId", data.getDestinationId())
                    .whereEqualTo("url", data.getUrl())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();

                            if (querySnapshot.isEmpty()) {
                                // Document doesn't exist, proceed with the upload
                                DocumentReference documentReference = instance.collection(ImageGallery.collectionName).document();
                                batch.set(documentReference, imageGalleryToMap(data));
                            } else {
                                // Document already exists, handle accordingly (you can skip or update)
                                Log.d(TAG, "Document already exists for destinationId: " + data.getDestinationId() +
                                        " and url: " + data.getUrl());
                            }

                            // Check if this is the last document in the loop before committing the batch
                            if (isLastDocument(data, imageGalleries)) {
                                // Commit the batch after checking all documents
                                batch.commit()
                                        .addOnSuccessListener(aVoid -> {
                                            Log.d(TAG, "Batch upload successful");
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.d(TAG, "Error uploading batch: " + e.getMessage());
                                        });
                            }
                        } else {
                            // Handle errors in the query
                            Log.d(TAG, "Error checking for document existence: " + task.getException().getMessage());
                        }
                    });
        }
    }

    private boolean isLastDocument(ImageGallery currentDocument, List<ImageGallery> allDocuments) {
        // Check if the current document is the last one in the list
        return currentDocument.equals(allDocuments.get(allDocuments.size() - 1));
    }


    // Helper method to convert ImageGallery object to a map
    private Map<String, Object> imageGalleryToMap(ImageGallery imageGallery) {
        Map<String, Object> map = new HashMap<>();
        map.put(ImageGallery.destinationIdField, imageGallery.getDestinationId());
        map.put(ImageGallery.urlField, imageGallery.getUrl());
        return map;
    }

    // Method to get the file name and extension from a Uri
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

    // Method to load the uploaded cover image
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
                                .into(imageView);
                    }
                });
            }
        }).addOnFailureListener(e -> {
            Picasso.get().load(R.drawable.sample);
        });
    }

    // Method to load the uploaded cover image with a callback
    public void loadUploadedImage(String destinationId, ImageView imageView, OnLoadCover loadCover) {
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
                                .into(imageView);

                        if (loadCover != null) {
                            loadCover.OnLoad(task.getResult());
                        }
                    }
                });
            }
        }).addOnFailureListener(e -> {
            Picasso.get().load(R.drawable.sample);
        });
    }

    // Method to load gallery images
    public void loadGalleryImage(String destinationId, OnImageLoadListener<ImageGallery> listener) {
        instance.collection(ImageGallery.collectionName).whereEqualTo(ImageGallery.destinationIdField, destinationId)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<ImageGallery> list = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            ImageGallery imageGallery = queryDocumentSnapshot.toObject(ImageGallery.class);
                            Log.d(TAG, "retrieve " + imageGallery.getUrl());
                            list.add(imageGallery);
                        }
                        if (listener != null) {
                            listener.onImageLoadSuccess(list);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (listener != null) {
                            listener.onImageLoadFailure(e);
                        }
                    }
                });
    }

    // Method to delete an image
    public void deleteImage(Uri uri, OnDeleteImageCallback deleteImageCallback) {
        CollectionReference collectionReference = instance.collection(ImageGallery.collectionName);

        Query query = collectionReference.whereEqualTo(ImageGallery.urlField, uri.toString());

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot.size() > 0) {
                    // Get the document reference
                    StorageReference storageRef = storage.getReferenceFromUrl(uri.toString());
                    storageRef.delete().addOnCompleteListener(storageTask -> {
                        if (storageTask.isSuccessful()) {
                            // Delete the document from Firestore after successful storage deletion
                            querySnapshot.getDocuments().get(0).getReference()
                                    .delete()
                                    .addOnSuccessListener(aVoid -> {
                                        if (deleteImageCallback != null) {
                                            deleteImageCallback.onSuccess();
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        if (deleteImageCallback != null) {
                                            deleteImageCallback.onFail();
                                        }
                                    });
                        } else {
                            // Handle errors during storage deletion
                            if (deleteImageCallback != null) {
                                deleteImageCallback.onFail();
                            }
                        }
                    });
                } else {
                    // No matching documents found
                    if (deleteImageCallback != null) {
                        deleteImageCallback.onFail();
                    }
                }
            } else {
                // Handle errors in the query
                if (deleteImageCallback != null) {
                    deleteImageCallback.onFail();
                }
            }
        });
    }
}
