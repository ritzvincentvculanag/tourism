package io.github.rmmc.rmmctourism.adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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
import io.github.rmmc.rmmctourism.util.OnDestinationDelete;
import io.github.rmmc.rmmctourism.util.OnDestinationUpdate;
import io.github.rmmc.rmmctourism.views.EditDestination;

public class MyDestinationAdapter extends RecyclerView.Adapter<MyDestinationAdapter.MyDestinationViewHolder> {

    private List<Destination> list;
    private Context context;
    private ImageRepository imageRepository;
    private DestinationRepository destinationRepository;

    public MyDestinationAdapter(List<Destination> list, Context context) {
        this.list = list;
        this.context = context;
        imageRepository = new ImageRepository();
        destinationRepository = new DestinationRepository(context);
    }

    @NonNull
    @Override
    public MyDestinationAdapter.MyDestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_my_destination, parent, false);

        return new MyDestinationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDestinationAdapter.MyDestinationViewHolder holder, int position) {
        Destination destination = list.get(position);
        holder.tvTitle.setText(destination.getName());
        holder.tvAddress.setText(destination.getAddress());
        holder.tvDescription.setText(destination.getDescription());
        imageRepository.loadUploadedImage(destination.getDestinationId(), holder.ivCover);
        holder.btnUpdate.setOnClickListener(e ->{
            Intent intent = new Intent(context, EditDestination.class);

            intent.putExtra(Destination.collectioName, destination);

            context.startActivity(intent);
        });
        holder.btnDelete.setOnClickListener(e ->{
            Messenger.showAlertDialog(context,
                    "Delete Destination",
                    "Do you want to delete the " + destination.getName() + "?",
                    "Yes",
                    "No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            destinationRepository.deleteDestination(destination.getDestinationId());
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
        return list.size();
    }

    public static class MyDestinationViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivCover;
        private TextView tvTitle;
        private TextView tvAddress;
        private TextView tvDescription;

        private Button btnUpdate;
        private Button btnDelete;

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
