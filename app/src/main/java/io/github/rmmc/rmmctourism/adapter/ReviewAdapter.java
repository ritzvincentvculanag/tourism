/**
 * ReviewAdapter is a RecyclerView adapter responsible for displaying a list of
 * reviews associated with a destination in the RMMC Tourism app.
 *
 * @param reviewList The list of Review items to be displayed in the adapter.
 * @param userInformations The list of UserInformation items associated with the reviews.
 * <p>
 * Usage:
 * // Example with a list of Review items and UserInformation items
 * List<Review> reviewList = //... populate the list
 * List<UserInformation> userInformations = //... populate the list
 * ReviewAdapter adapter = new ReviewAdapter(reviewList, userInformations);
 */
package io.github.rmmc.rmmctourism.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;

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

    /**
     * Constructs a ReviewAdapter with a specified list of Review items and UserInformation items.
     *
     * @param reviewList The list of Review items to be displayed in the adapter.
     * @param userInformations The list of UserInformation items associated with the reviews.
     */
    public ReviewAdapter(List<Review> reviewList, List<UserInformation> userInformations) {
        this.reviewList = reviewList;
        this.userInformations = userInformations;
    }

    /**
     * Creates and returns a new instance of ReviewViewHolder.
     *
     * @param parent The parent ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new ReviewViewHolder instance.
     */
    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_review, parent, false);
        return new ReviewViewHolder(view);
    }

    /**
     * Binds the data at the specified position to the given ReviewViewHolder.
     *
     * @param holder The ReviewViewHolder to bind data to.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.tvDatePublished.setText(formatTimestamp(review.getDatePublished()));
        holder.tvContent.setText(review.getContent());
        for (UserInformation userInformation : userInformations) {
            if (userInformation.getUID().equals(review.getUserId())) {
                holder.tvName.setText(formatName(userInformation));
            }
        }
    }

    /**
     * Returns the total number of items that can be displayed by the adapter.
     *
     * @return The total number of items.
     */
    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    /**
     * Formats the name from the given UserInformation.
     *
     * @param userInformation The UserInformation instance.
     * @return The formatted name.
     */
    private String formatName(UserInformation userInformation) {
        return userInformation.getFirstName() + " " + userInformation.getLastName();
    }

    /**
     * Formats the timestamp to a readable date and time string.
     *
     * @param timestamp The Timestamp instance.
     * @return The formatted date and time string.
     */
    private String formatTimestamp(Timestamp timestamp) {
        if (timestamp != null) {
            Date date = timestamp.toDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault());
            return dateFormat.format(date);
        } else {
            return "";
        }
    }

    /**
     * ReviewViewHolder is a RecyclerView.ViewHolder implementation for holding
     * views associated with items in the ReviewAdapter.
     */
    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvContent;
        private TextView tvDatePublished;

        /**
         * Constructs a ReviewViewHolder with a specified View.
         *
         * @param view The View associated with the ViewHolder.
         */
        public ReviewViewHolder(@NonNull View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_review_name);
            tvContent = view.findViewById(R.id.tv_review_content);
            tvDatePublished = view.findViewById(R.id.tv_review_date_published);
        }
    }
}
