/**
 * MyDestinationAdapter is a RecyclerView adapter responsible for displaying a list of
 * user-created Destination items in the My Destinations section of the RMMC Tourism app.
 * <p>
 * This adapter provides the ability to update and delete user-created destinations.
 *
 * @param list The list of Destination items to be displayed in the adapter.
 * @param context The context of the calling activity or fragment.
 * <p>
 * Usage:
 * // Example with a list of Destination items and a context
 * List<Destination> destinationList = //... populate the list
 * Context context = //... obtain the context
 * MyDestinationAdapter adapter = new MyDestinationAdapter(destinationList, context);
 */
package io.github.rmmc.rmmctourism.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.repository.DestinationRepository;
import io.github.rmmc.rmmctourism.repository.ImageRepository;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.views.EditDestination;

public class MyDestinationAdapter extends RecyclerView.Adapter<MyDestinationAdapter.MyDestinationViewHolder> {

    private List<Destination> list;
    private Context context;
    private ImageRepository imageRepository;
    private DestinationRepository destinationRepository;

    /**
     * Constructs a MyDestinationAdapter with a specified list of Destination items and a context.
     *
     * @param list The list of Destination items to be displayed in the adapter.
     * @param context The context of the calling activity or fragment.
     */
    public MyDestinationAdapter(List<Destination> list, Context context) {
        this.list = list;
        this.context = context;
        imageRepository = new ImageRepository();
        destinationRepository = new DestinationRepository(context);
    }

    /**
     * Creates and returns a new instance of MyDestinationViewHolder.
     *
     * @param parent The parent ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new MyDestinationViewHolder instance.
     */
    @NonNull
    @Override
    public MyDestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_my_destination, parent, false);
        return new MyDestinationViewHolder(view);
    }

    /**
     * Binds the data at the specified position to the given MyDestinationViewHolder.
     *
     * @param holder The MyDestinationViewHolder to bind data to.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MyDestinationViewHolder holder, int position) {
        Destination destination = list.get(position);
        holder.tvTitle.setText(destination.getName());
        holder.tvAddress.setText(destination.getAddress());
        holder.tvDescription.setText(destination.getDescription());
        imageRepository.loadUploadedImage(destination.getDestinationId(), holder.ivCover);
        holder.btnUpdate.setOnClickListener(e -> {
            Intent intent = new Intent(context, EditDestination.class);
            intent.putExtra(Destination.collectioName, destination);
            context.startActivity(intent);
        });
        int index = position;
        holder.btnDelete.setOnClickListener(e -> {
            Messenger.showAlertDialog(context,
                    "Delete Destination",
                    "Do you want to delete the " + destination.getName() + "?",
                    "Yes",
                    "No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            destinationRepository.deleteDestination(destination.getDestinationId());
                            list.remove(index);
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
        return list.size();
    }

    /**
     * MyDestinationViewHolder is a RecyclerView.ViewHolder implementation for holding
     * views associated with items in the MyDestinationAdapter.
     */
    public static class MyDestinationViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivCover;
        private TextView tvTitle;
        private TextView tvAddress;
        private TextView tvDescription;

        private Button btnUpdate;
        private Button btnDelete;

        /**
         * Constructs a MyDestinationViewHolder with a specified View.
         *
         * @param view The View associated with the ViewHolder.
         */
        public MyDestinationViewHolder(@NonNull View view) {
            super(view);
            ivCover = view.findViewById(R.id.iv_my_destination_cover);
            tvTitle = view.findViewById(R.id.tv_my_destination_title);
            tvAddress = view.findViewById(R.id.tv_my_destination_address);
            tvDescription = view.findViewById(R.id.tv_my_destination_description);
            btnUpdate = view.findViewById(R.id.btn_my_destination_update);
            btnDelete = view.findViewById(R.id.btn_my_destination_delete);
        }
    }
}
