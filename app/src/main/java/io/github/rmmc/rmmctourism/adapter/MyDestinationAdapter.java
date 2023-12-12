package io.github.rmmc.rmmctourism.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.util.OnDestinationDelete;
import io.github.rmmc.rmmctourism.util.OnDestinationUpdate;

public class MyDestinationAdapter extends RecyclerView.Adapter<MyDestinationAdapter.MyDestinationViewHolder> {

    private OnDestinationDelete onDestinationDelete;
    private OnDestinationUpdate onDestinationUpdate;

    public MyDestinationAdapter(OnDestinationDelete onDestinationDelete, OnDestinationUpdate onDestinationUpdate) {
        this.onDestinationDelete = onDestinationDelete;
        this.onDestinationUpdate = onDestinationUpdate;
    }

    @NonNull
    @Override
    public MyDestinationAdapter.MyDestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_my_destination, parent, false);

        return new MyDestinationViewHolder(onDestinationDelete, onDestinationUpdate, view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDestinationAdapter.MyDestinationViewHolder holder, int position) {
        // TODO: Handle onBindViewHolder
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyDestinationViewHolder extends RecyclerView.ViewHolder {

        private OnDestinationDelete onDestinationDelete;
        private OnDestinationUpdate onDestinationUpdate;

        private ImageView ivCover;
        private TextView tvTitle;
        private TextView tvAddress;
        private TextView tvDescription;

        private Button btnUpdate;
        private Button btnDelete;

        public MyDestinationViewHolder(OnDestinationDelete onDestinationDelete,
                                       OnDestinationUpdate onDestinationUpdate,
                                       @NonNull View view) {
            super(view);

            this.onDestinationDelete = onDestinationDelete;
            this.onDestinationUpdate = onDestinationUpdate;

            ivCover = view.findViewById(R.id.iv_my_destination_cover);
            tvTitle = view.findViewById(R.id.tv_my_destination_title);
            tvAddress = view.findViewById(R.id.tv_my_destination_address);
            tvDescription = view.findViewById(R.id.tv_my_destination_description);

            btnUpdate = view.findViewById(R.id.btn_my_destination_update);
            btnDelete = view.findViewById(R.id.btn_my_destination_delete);

            btnUpdate.setOnClickListener(e -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onDestinationUpdate.update(getAdapterPosition());
                }
            });
            btnDelete.setOnClickListener(e -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onDestinationDelete.delete(getAdapterPosition());
                }
            });
        }
    }

}
