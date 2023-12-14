package io.github.rmmc.rmmctourism.adapter;

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

public class GalleryItemAdapter extends RecyclerView.Adapter<GalleryItemAdapter.GalleryItemViewHolder> {

    private List<ImageGallery> uris;

    public GalleryItemAdapter(List<ImageGallery> images) {
        uris = images;
    }

    @NonNull
    @Override
    public GalleryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_gallery_item, parent, false);

        return new GalleryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryItemViewHolder holder, int position) {
        ImageGallery url = uris.get(position);
        Log.d(TAG, "he " + uris.toString());
        Picasso.get()
                .load(url.getUrl())
                .placeholder(R.drawable.sample)
                .error(R.drawable.sample) // Image to display in case of an error
                .into(holder.galleryItem);
    }

    @Override
    public int getItemCount() {
        return uris.size();
    }


    public static class GalleryItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView galleryItem;

        public GalleryItemViewHolder(@NonNull View itemView) {
            super(itemView);

            galleryItem = itemView.findViewById(R.id.iv_gallery_item);
        }
    }

}
