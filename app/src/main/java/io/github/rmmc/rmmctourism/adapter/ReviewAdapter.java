package io.github.rmmc.rmmctourism.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.github.rmmc.rmmctourism.R;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_review, parent, false);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        // TODO: Handle onBindViewHolder
    }

    @Override
    public int getItemCount() {
        // TODO: Handle getItemCount

        return 0;
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvContent;

        public ReviewViewHolder(@NonNull View view) {
            super(view);

            tvName = view.findViewById(R.id.tv_review_name);
            tvContent = view.findViewById(R.id.tv_review_content);
        }
    }

}
