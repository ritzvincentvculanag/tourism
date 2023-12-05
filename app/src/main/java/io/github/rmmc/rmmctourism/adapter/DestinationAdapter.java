package io.github.rmmc.rmmctourism.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.views.TouristSpotInformationActivity;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder> {

    private final Context context;
    private final List<Destination> list;

    public DestinationAdapter(Context context, List<Destination> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_spot_row, parent, false);

        return new DestinationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationViewHolder holder, int position) {
        holder.cvTouristSpot.setOnClickListener(e ->{
            context.startActivity(new Intent(this.context, TouristSpotInformationActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class DestinationViewHolder extends RecyclerView.ViewHolder {

        private final MaterialCardView cvTouristSpot;
        private final ImageView imgTouristCover;
        private final TextView tvTouristSpotName;
        private final TextView tvTouristDescription;

        public DestinationViewHolder(@NonNull View itemView) {
            super(itemView);

            cvTouristSpot = itemView.findViewById(R.id.cv_tourist_spot);
            imgTouristCover = itemView.findViewById(R.id.img_tourist_cover);
            tvTouristSpotName = itemView.findViewById(R.id.tv_tourist_spot_name);
            tvTouristDescription = itemView.findViewById(R.id.tv_tourist_spot_description);
        }
    }
}
