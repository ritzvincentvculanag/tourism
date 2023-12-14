/**
 * GalleryLoadAdapter is a RecyclerView adapter responsible for loading and displaying
 * images in the gallery of a destination in the RMMC Tourism app.
 * <p>
 * This adapter is designed to work with the RecyclerView in the destination detail view
 * and efficiently loads and displays a list of ImageGallery items using the Picasso library.
 *
 * @param list The list of ImageGallery items to be displayed in the gallery.
 * <p>
 * Usage:
 * // Example with a list of ImageGallery items
 * List<ImageGallery> galleryList = //... populate the list
 * GalleryLoadAdapter adapter = new GalleryLoadAdapter(galleryList);
 */
package io.github.rmmc.rmmctourism.adapter.viewpager;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.model.ImageGallery;

public class GalleryLoadAdapter extends RecyclerView.Adapter<GalleryLoadAdapter.GalleryViewHolder> {

    private List<ImageGallery> list;

    /**
     * Constructs a GalleryLoadAdapter with a specified list of ImageGallery items.
     *
     * @param list The list of ImageGallery items to be displayed in the gallery.
     */
    public GalleryLoadAdapter(List<ImageGallery> list) {
        this.list = list;
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
        ImageGallery url = list.get(position);
        Log.d(TAG, "he " + list.toString());
        Picasso.get()
                .load(url.getUrl())
                .placeholder(R.drawable.sample)
                .error(R.drawable.sample) // Image to display in case of an error
                .into(holder.ivGalleryItem);
    }

    /**
     * Returns the total number of items that can be displayed by the adapter.
     *
     * @return The total number of items.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * GalleryViewHolder is a RecyclerView.ViewHolder implementation for holding
     * views associated with items in the GalleryLoadAdapter.
     */
    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivGalleryItem;

        /**
         * Constructs a GalleryViewHolder with a specified View.
         *
         * @param itemView The View associated with the ViewHolder.
         */
        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGalleryItem = itemView.findViewById(R.id.iv_gallery_item);
        }
    }
}
