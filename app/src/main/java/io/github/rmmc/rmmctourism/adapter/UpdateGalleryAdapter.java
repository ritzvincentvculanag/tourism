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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.model.ImageGallery;
import io.github.rmmc.rmmctourism.repository.ImageRepository;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.util.OnDeleteImageCallback;

public class UpdateGalleryAdapter extends RecyclerView.Adapter<UpdateGalleryAdapter.GalleryViewHolder> {

    private List<Uri> uris;
    private Context context;
    private ImageRepository imageRepository;
    public UpdateGalleryAdapter(List<Uri> uris, Context context) {
        this.context = context;
        this.uris = new ArrayList<>(uris);
        this.imageRepository = new ImageRepository();
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
        Log.d(TAG,"update" +  uri.toString());
        Picasso.get().load(uri).placeholder(R.drawable.sample).into(holder.ivGalleryItem);
        holder.ivGalleryItem.setOnClickListener(e ->{
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
                                            "Image successfully remove from gallery","Ok").show();
                                }

                                @Override
                                public void onFail() {
                                    uris.remove(index);
                                    notifyDataSetChanged();
                                    Messenger.showAlertDialog(context,
                                            "Delete Image",
                                            "Image successfully remove from gallery","Ok").show();
                                }
                            });
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
        this.uris.addAll(newUris);
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
