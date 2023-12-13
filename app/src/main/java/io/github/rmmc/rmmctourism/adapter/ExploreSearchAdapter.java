/**
 * ExploreSearchAdapter is a RecyclerView adapter responsible for displaying search results
 * of destination items in the Explore section of the RMMC Tourism app.
 *
 * This adapter supports the dynamic loading of destination images and provides a button
 * to view the details of a selected destination.
 *
 * @param context The context of the calling activity or fragment.
 * @param list The list of Destination items to be displayed.
 *
 * Usage:
 * // Example with a list of Destination items
 * List<Destination> destinationList = //... populate the list
 * ExploreSearchAdapter adapter = new ExploreSearchAdapter(context, destinationList);
 */
package io.github.rmmc.rmmctourism.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.repository.ImageRepository;
import io.github.rmmc.rmmctourism.views.DestinationDetail;

public class ExploreSearchAdapter extends RecyclerView.Adapter<ExploreSearchAdapter.MyViewHolder> {

    private Context context;
    private List<Destination> list;
    private ImageRepository imageRepository;

    /**
     * Constructs an ExploreSearchAdapter with a specified context and list of Destination items.
     *
     * @param context The context of the calling activity or fragment.
     * @param list The list of Destination items to be displayed.
     */
    public ExploreSearchAdapter(Context context, List<Destination> list){
        this.context = context;
        this.list = new ArrayList<>(list);
        this.imageRepository = new ImageRepository();
    }

    /**
     * Creates and returns a new instance of MyViewHolder.
     *
     * @param parent The parent ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new MyViewHolder instance.
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_destination_search, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * Binds the data at the specified position to the given MyViewHolder.
     *
     * @param holder The MyViewHolder to bind data to.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Destination destination = list.get(position);
        holder.tvTitle.setText(destination.getName());
        holder.tvDescription.setText(destination.getDescription());
        holder.tvAddress.setText(destination.getAddress());
        imageRepository.loadUploadedImage(destination.getDestinationId(), holder.ivCoverImg);
        holder.btnViewDestination.setOnClickListener(e -> {
            Intent intent = new Intent(context, DestinationDetail.class);
            intent.putExtra(Destination.collectioName, destination);
            context.startActivity(intent);
        });
    }

    /**
     * Updates the adapter's data with the provided filtered data.
     *
     * @param filterData The filtered list of Destination items.
     */
    public void searchDestination(List<Destination> filterData) {
        list.clear();
        list.addAll(filterData);
        notifyDataSetChanged();
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
     * MyViewHolder is a RecyclerView.ViewHolder implementation for holding
     * views associated with items in the ExploreSearchAdapter.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivCoverImg;
        private TextView tvTitle, tvDescription, tvAddress;
        private Button btnViewDestination;

        /**
         * Constructs a MyViewHolder with a specified View.
         *
         * @param itemView The View associated with the ViewHolder.
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCoverImg = itemView.findViewById(R.id.iv_destination_search_cover);
            tvTitle = itemView.findViewById(R.id.tv_destination_search_title);
            tvDescription = itemView.findViewById(R.id.tv_destination_search_description);
            tvAddress = itemView.findViewById(R.id.tv_destination_search_address);
            btnViewDestination = itemView.findViewById(R.id.btn_view_destination);
        }
    }
}
