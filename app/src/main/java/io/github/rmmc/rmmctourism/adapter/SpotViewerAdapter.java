package io.github.rmmc.rmmctourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.model.TouristSpotModel;

public class SpotViewerAdapter extends RecyclerView.Adapter<SpotViewerAdapter.MyViewHolder> {

    private Context context;
    private List<TouristSpotModel> list;
    public SpotViewerAdapter(Context context, List<TouristSpotModel> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public SpotViewerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_spot_row , parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SpotViewerAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgTouristSpot;
        private TextView tvTouristSpotName;
        private TextView tvDescription;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTouristSpot = itemView.findViewById(R.id.img_tourist_spot);
            tvTouristSpotName = itemView.findViewById(R.id.tv_tourist_spot_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }
    }
}
