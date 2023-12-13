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

import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.util.Messenger;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private List<Uri> uris;
    private Context context;

    public GalleryAdapter(List<Uri> uris, Context context) {
        this.context = context;
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
