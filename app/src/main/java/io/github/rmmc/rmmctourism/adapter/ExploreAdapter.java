package io.github.rmmc.rmmctourism.adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.model.Favorite;
import io.github.rmmc.rmmctourism.repository.DestinationRepository;
import io.github.rmmc.rmmctourism.repository.FavoriteRepository;
import io.github.rmmc.rmmctourism.repository.ImageRepository;
import io.github.rmmc.rmmctourism.util.DestinationDataCallback;
import io.github.rmmc.rmmctourism.util.ImageDataCallback;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder> {

    private List<Favorite> favorites;
    private List<Destination> list;
    private Context context;
    private ImageRepository imageRepository;
    private FirebaseAuth userAuth;
    private FavoriteRepository favoriteRepository;
    public ExploreAdapter(Context context, List<Destination> list){
        this.context = context;
        this.list = list;
        imageRepository = new ImageRepository();
        favoriteRepository = new FavoriteRepository(context);
        userAuth = FirebaseAuth.getInstance();
    }

    public ExploreAdapter(Context context, List<Destination> list, List<Favorite> favorites){
        this.context = context;
        this.list = new ArrayList<>(list);
        this.favorites = new ArrayList<>(favorites);
        imageRepository = new ImageRepository();
        favoriteRepository = new FavoriteRepository(context);
        userAuth = FirebaseAuth.getInstance();
    }
    @NonNull
    @Override
    public ExploreAdapter.ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_destination, parent, false);
        return new ExploreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreAdapter.ExploreViewHolder holder, int position) {

        Destination destination = list.get(position);

        holder.tvTitle.setText(destination.getName());
        holder.tvAddress.setText(destination.getAddress());
        holder.tvDescription.setText(destination.getDescription());

        imageRepository.loadUploadedImage(destination.getDestinationId(), holder.coverImg);
        for (Iterator<Favorite> iterator = favorites.iterator(); iterator.hasNext();) {
            Favorite favorite = iterator.next();

            if (favorite.getDestinationId().equals(destination.getDestinationId())) {
                holder.btnFavorite.setBackgroundColor(Color.parseColor("#FF0000"));
                holder.btnFavorite.setOnClickListener(e -> {
                    favoriteRepository.removeFavorite(favorite);
                    holder.btnFavorite.setBackgroundColor(Color.parseColor("#6750a4"));
                    iterator.remove();
                    notifyDataSetChanged();
                });
                return;
            }
        }

        holder.btnFavorite.setOnClickListener(e ->{
            favoriteRepository.addFavorite(new Favorite(userAuth.getCurrentUser().getUid(), destination.getDestinationId()));
            favorites.add(new Favorite(userAuth.getCurrentUser().getUid(), destination.getDestinationId()));
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ExploreViewHolder extends RecyclerView.ViewHolder {

        private ImageView coverImg;
        private TextView tvTitle;
        private TextView tvAddress;
        private TextView tvDescription;
        private Button btnFavorite;
        public ExploreViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImg = itemView.findViewById(R.id.iv_cover);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvAddress = itemView.findViewById(R.id.tv_address);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
        }
    }
}
