// Package declaration
package io.github.rmmc.rmmctourism.views;

// Import statements
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.model.Review;
import io.github.rmmc.rmmctourism.repository.ReviewRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.Messenger;
import io.github.rmmc.rmmctourism.util.Miner;
import io.github.rmmc.rmmctourism.util.Validator;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

// AddReview class definition
public class AddReview extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    // UI components
    private TextInputLayout tfReview;
    private Button btnAddReview;

    // Repositories
    private ReviewRepository reviewRepository;
    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        // Initialize repositories
        reviewRepository = new ReviewRepository(this);
        userAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        initializeWidgets();

        // Set up button click action
        initializeActions();
    }

    @Override
    public void initializeActions() {
        // Action: Add review when the button is clicked
        btnAddReview.setOnClickListener(this::addReview);
    }

    @Override
    public void initializeWidgets() {
        // Initialize UI components
        tfReview = findViewById(R.id.til_add_review_content);
        btnAddReview = findViewById(R.id.btn_add_review);
    }

    private void addReview(View view) {
        // Validate and add review logic
        if (Validator.fieldIsEmpty(tfReview)) {
            Messenger.showAlertDialog(
                    this,
                    "Add Review",
                    "Please enter your review before submitting.",
                    "Ok"
            ).show();
            return;
        }

        // Get destination data from intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Destination.collectioName)) {
            Destination destination = getIntent().getParcelableExtra(Destination.collectioName);
            Review review = new Review(
                    userAuth.getCurrentUser().getUid(),
                    destination.getDestinationId(),
                    Miner.getString(tfReview),
                    Timestamp.now()
            );

            // Add the review to the repository
            reviewRepository.addReview(review);
            tfReview.getEditText().setText("");
        }
    }
}
