package io.github.rmmc.rmmctourism.adapter;

import android.content.Context;
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

public class ExploreSearchAdapter extends RecyclerView.Adapter<ExploreSearchAdapter.MyViewHolder> {

    private Context context;
    private List<Destination> list;
    private ImageRepository imageRepository;
    public ExploreSearchAdapter(Context context, List<Destination> list){
        this.context = context;
        this.list = new ArrayList<>(list);
        this.imageRepository = new ImageRepository();
    }
    @NonNull
    @Override
    public ExploreSearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_destination_search, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreSearchAdapter.MyViewHolder holder, int position) {
        Destination destination = list.get(position);
        holder.tvTitle.setText(destination.getName());
        holder.tvDescription.setText(destination.getDescription());
        holder.tvAddress.setText(destination.getAddress());
        imageRepository.loadUploadedImage(destination.getDestinationId(), holder.ivCoverImg);
    }

    public void searchDestination(List<Destination> filterData) {
        list.clear();
        list.addAll(filterData);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivCoverImg;
        private TextView tvTitle, tvDescription, tvAddress;
        private Button btnViewDestination;
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
