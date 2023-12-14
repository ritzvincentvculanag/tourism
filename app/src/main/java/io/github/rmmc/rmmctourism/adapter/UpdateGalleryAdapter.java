/**
 * This is an adapter class for updating the gallery in the destination details screen.
 * It provides functionality to display and manage a list of images in a RecyclerView.
 * The adapter includes a ViewHolder for efficient item view management and uses Picasso for image loading.
 */
package io.github.rmmc.rmmctourism.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.repository.ImageRepository;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.util.OnDeleteImageCallback;

public class UpdateGalleryAdapter extends RecyclerView.Adapter<UpdateGalleryAdapter.GalleryViewHolder> {

    private static final String TAG = UpdateGalleryAdapter.class.getSimpleName();

    private List<Uri> uris;
    private Context context;
    private ImageRepository imageRepository;

    /**
     * Constructor for the UpdateGalleryAdapter.
     *
     * @param uris    The list of URIs representing images in the gallery.
     * @param context The context in which the adapter is used.
     */
    public UpdateGalleryAdapter(List<Uri> uris, Context context) {
        this.context = context;
        this.uris = new ArrayList<>(uris);
        this.imageRepository = new ImageRepository();
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added.
     * @param viewType The type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_destination_gallery_item, parent, false);

        return new GalleryViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        Uri uri = uris.get(position);
        Log.d(TAG, "update" + uri.toString());
        Picasso.get().load(uri).placeholder(R.drawable.sample).into(holder.ivGalleryItem);
        holder.ivGalleryItem.setOnClickListener(e -> {
            int index = position;
            Messenger.showAlertDialog(context,
                    "Delete Image",
                    "Do you want to remove the image in the gallery?",
                    "Yes", "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            imageRepository.deleteImage(uri, new OnDeleteImageCallback() {
                                @Override
                                public void onSuccess() {
                                    uris.remove(index);
                                    notifyDataSetChanged();

                                    Messenger.showAlertDialog(context,
                                            "Delete Image",
                                            "Image successfully removed from the gallery", "Ok").show();
                                }

                                @Override
                                public void onFail() {
                                    uris.remove(index);
                                    notifyDataSetChanged();
                                    Messenger.showAlertDialog(context,
                                            "Delete Image",
                                            "Image removal from the gallery failed", "Ok").show();
                                }
                            });
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // No action on cancel
                        }
                    }).show();
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return uris.size();
    }

    /**
     * Refreshes the list of URIs in the adapter.
     *
     * @param newUris The new list of URIs to be displayed in the gallery.
     */
    public void refreshUris(List<Uri> newUris) {
        this.uris.addAll(newUris);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class for the gallery item.
     */
    public static class GalleryViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivGalleryItem;

        /**
         * Constructor for the GalleryViewHolder.
         *
         * @param view The view representing a single item in the gallery.
         */
        public GalleryViewHolder(@NonNull View view) {
            super(view);

            ivGalleryItem = view.findViewById(R.id.iv_gallery_item);
        }
    }
}
