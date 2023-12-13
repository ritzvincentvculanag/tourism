package io.github.rmmc.rmmctourism.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.model.Review;
import io.github.rmmc.rmmctourism.model.UserInformation;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<UserInformation> userInformations;
    private List<Review> reviewList;

    public ReviewAdapter(List<Review> reviewList, List<UserInformation> userInformations){
        this.reviewList = reviewList;
        this.userInformations = userInformations;
    }
    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_review, parent, false);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.tvDatePublished.setText(formatTimestamp(review.getDatePublished()));
        holder.tvContent.setText(review.getContent());
        for(UserInformation userInformation: userInformations){
            if(userInformation.getUID().equals(review.getUserId())){
                holder.tvName.setText(formatName(userInformation));
            }
        }

    }
    private String formatName(UserInformation userInformation){
        return userInformation.getFirstName() + " " + userInformation.getMiddleName().charAt(0) + ". " + userInformation.getLastName();
    }

    private String formatTimestamp(Timestamp timestamp) {
        if (timestamp != null) {
            Date date = timestamp.toDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault());
            return dateFormat.format(date);
        } else {
            return "";
        }
    }


    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvContent;
        private TextView tvDatePublished;

        public ReviewViewHolder(@NonNull View view) {
            super(view);

            tvName = view.findViewById(R.id.tv_review_name);
            tvContent = view.findViewById(R.id.tv_review_content);
            tvDatePublished = view.findViewById(R.id.tv_review_date_published);
        }
    }

}
