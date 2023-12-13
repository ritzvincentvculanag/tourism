package io.github.rmmc.rmmctourism.adapter.viewpager;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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
import io.github.rmmc.rmmctourism.adapter.GalleryAdapter;
import io.github.rmmc.rmmctourism.model.ImageGallery;

public class GalleryLoadAdapter extends RecyclerView.Adapter<GalleryLoadAdapter.GalleryViewHolder>{

    private List<ImageGallery> list;

    public GalleryLoadAdapter(List<ImageGallery> list){
        this.list = list;
    }
    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_destination_gallery_item, parent, false);
        return new  GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        ImageGallery url = list.get(position);
        Log.d(TAG,  "he "+ list.toString());
        Picasso.get()
                .load(url.getUrl())
                .placeholder(R.drawable.sample)
                .error(R.drawable.sample) // Image to display in case of an error
                .into(holder.ivGalleryItem);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivGalleryItem;
        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGalleryItem = itemView.findViewById(R.id.iv_gallery_item);
        }
    }
}
