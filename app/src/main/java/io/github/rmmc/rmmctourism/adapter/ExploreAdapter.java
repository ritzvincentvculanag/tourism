package io.github.rmmc.rmmctourism.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder> {

    @NonNull
    @Override
    public ExploreAdapter.ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreAdapter.ExploreViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ExploreViewHolder extends RecyclerView.ViewHolder {
        public ExploreViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
