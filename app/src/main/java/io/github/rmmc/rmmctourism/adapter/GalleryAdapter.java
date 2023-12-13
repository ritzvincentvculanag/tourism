/**
 * GalleryAdapter is a RecyclerView adapter responsible for displaying a list of
 * Uri images in a gallery for the destination in the RMMC Tourism app.
 *
 * This adapter provides the ability to delete images from the gallery.
 *
 * @param uris The list of Uri images to be displayed in the gallery.
 * @param context The context of the calling activity or fragment.
 *
 * Usage:
 * // Example with a list of Uri images and a context
 * List<Uri> uriList = //... populate the list
 * Context context = //... obtain the context
 * GalleryAdapter adapter = new GalleryAdapter(uriList, context);
 */
package io.github.rmmc.rmmctourism.adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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

import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.util.Messenger;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private List<Uri> uris;
    private Context context;

    /**
     * Constructs a GalleryAdapter with a specified list of Uri images and a context.
     *
     * @param uris The list of Uri images to be displayed in the gallery.
     * @param context The context of the calling activity or fragment.
     */
    public GalleryAdapter(List<Uri> uris, Context context) {
        this.context = context;
        this.uris = uris;
    }

    /**
     * Creates and returns a new instance of GalleryViewHolder.
     *
     * @param parent The parent ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new GalleryViewHolder instance.
     */
    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_destination_gallery_item, parent, false);
        return new GalleryViewHolder(view);
    }

    /**
     * Binds the data at the specified position to the given GalleryViewHolder.
     *
     * @param holder The GalleryViewHolder to bind data to.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        Uri uri = uris.get(position);
        Log.d(TAG, "adapter "+uri.toString());
        holder.ivGalleryItem.setImageURI(uri);
        holder.ivGalleryItem.setOnClickListener(e ->{
            int index = position;
            Messenger.showAlertDialog(context,
                    "Delete Image",
                    "Do you want to remove the image in the gallery?",
                    "Yes", "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            uris.remove(index);
                            notifyDataSetChanged();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
        });
    }

    /**
     * Returns the total number of items that can be displayed by the adapter.
     *
     * @return The total number of items.
     */
    @Override
    public int getItemCount() {
        return uris.size();
    }

    /**
     * Updates the adapter's data with the provided list of Uri images.
     *
     * @param newUris The new list of Uri images.
     */
    public void refreshUris(List<Uri> newUris) {
        this.uris = newUris;
        notifyDataSetChanged();
    }

    /**
     * GalleryViewHolder is a RecyclerView.ViewHolder implementation for holding
     * views associated with items in the GalleryAdapter.
     */
    public static class GalleryViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivGalleryItem;

        /**
         * Constructs a GalleryViewHolder with a specified View.
         *
         * @param view The View associated with the ViewHolder.
         */
        public GalleryViewHolder(@NonNull View view) {
            super(view);
            ivGalleryItem = view.findViewById(R.id.iv_gallery_item);
        }
    }
}
