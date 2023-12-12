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

public class MyDestinationAdapter extends RecyclerView.Adapter<MyDestinationAdapter.MyDestinationViewHolder> {

    @NonNull
    @Override
    public MyDestinationAdapter.MyDestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_my_destination, parent, false);

        return new MyDestinationViewHolder(view);
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
