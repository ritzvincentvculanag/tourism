/**
 * ExploreAdapter is a RecyclerView adapter responsible for displaying destination items
 * in the Explore section of the RMMC Tourism app.
 *
 * This adapter supports the dynamic loading of destination images, handling favorites,
 * and populating the UI with destination information.
 *
 * @param context The context of the calling activity or fragment.
 * @param list The list of Destination items to be displayed.
 * @param favorites The list of Favorite items representing user favorites.
 *
 * Usage:
 * // Example with a list of Destination items
 * List<Destination> destinationList = //... populate the list
 * ExploreAdapter adapter = new ExploreAdapter(context, destinationList);
 *
 * // Example with a list of Destination items and user favorites
 * List<Destination> destinationList = //... populate the list
 * List<Favorite> favoriteList = //... populate the list
 * ExploreAdapter adapter = new ExploreAdapter(context, destinationList, favoriteList);
 */
package io.github.rmmc.rmmctourism.adapter;

import android.content.Context;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.model.Favorite;
import io.github.rmmc.rmmctourism.repository.FavoriteRepository;
import io.github.rmmc.rmmctourism.repository.ImageRepository;


public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder> {

    private List<Favorite> favorites;
    private List<Destination> list;
    private Context context;
    private ImageRepository imageRepository;
    private FirebaseAuth userAuth;
    private FavoriteRepository favoriteRepository;

    /**
     * Constructs an ExploreAdapter with a specified context and list of Destination items.
     *
     * @param context The context of the calling activity or fragment.
     * @param list The list of Destination items to be displayed.
     */
    public ExploreAdapter(Context context, List<Destination> list){
        this.context = context;
        this.list = list;
        imageRepository = new ImageRepository();
        favoriteRepository = new FavoriteRepository(context);
        userAuth = FirebaseAuth.getInstance();
    }

    /**
     * Constructs an ExploreAdapter with a specified context, list of Destination items,
     * and list of user favorites.
     *
     * @param context The context of the calling activity or fragment.
     * @param list The list of Destination items to be displayed.
     * @param favorites The list of Favorite items representing user favorites.
     */
    public ExploreAdapter(Context context, List<Destination> list, List<Favorite> favorites){
        this.context = context;
        this.list = new ArrayList<>(list);
        this.favorites = new ArrayList<>(favorites);
        imageRepository = new ImageRepository();
        favoriteRepository = new FavoriteRepository(context);
        userAuth = FirebaseAuth.getInstance();
    }

    /**
     * Creates and returns a new instance of ExploreViewHolder.
     *
     * @param parent The parent ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new ExploreViewHolder instance.
     */
    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_destination, parent, false);
        return new ExploreViewHolder(view);
    }

    /**
     * Binds the data at the specified position to the given ExploreViewHolder.
     *
     * @param holder The ExploreViewHolder to bind data to.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {

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
     * ExploreViewHolder is a RecyclerView.ViewHolder implementation for holding
     * views associated with items in the ExploreAdapter.
     */
    public class ExploreViewHolder extends RecyclerView.ViewHolder {

        private ImageView coverImg;
        private TextView tvTitle;
        private TextView tvAddress;
        private TextView tvDescription;
        private Button btnFavorite;

        /**
         * Constructs an ExploreViewHolder with a specified View.
         *
         * @param itemView The View associated with the ViewHolder.
         */
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
