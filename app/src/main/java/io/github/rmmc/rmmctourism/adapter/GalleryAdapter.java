package io.github.rmmc.rmmctourism.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private List<Uri> uris;

    public GalleryAdapter(List<Uri> uris) {
        this.uris = uris;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_destination_gallery_item, parent, false);

        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        Uri uri = uris.get(position);
        holder.ivGalleryItem.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    public void refreshUris(List<Uri> newUris) {
        this.uris = newUris;

        notifyDataSetChanged();
    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivGalleryItem;

        public GalleryViewHolder(@NonNull View view) {
            super(view);

            ivGalleryItem = view.findViewById(R.id.iv_gallery_item);
        }
    }

}
