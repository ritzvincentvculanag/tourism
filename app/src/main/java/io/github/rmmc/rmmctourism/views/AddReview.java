package io.github.rmmc.rmmctourism.views;

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

public class AddReview extends AppCompatActivity implements WidgetInitializer, ActionInitializer {

    private TextInputLayout tfReview;
    private Button btnAddReview;
    private ReviewRepository reviewRepository;
    private FirebaseAuth userAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        reviewRepository = new ReviewRepository(this);
        userAuth = FirebaseAuth.getInstance();
        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeActions() {
        btnAddReview.setOnClickListener(this::addReview);
    }

    @Override
    public void initializeWidgets() {
        tfReview = findViewById(R.id.til_add_review_content);
        btnAddReview = findViewById(R.id.btn_add_review);
    }
    private void addReview(View view) {
        if (Validator.fieldIsEmpty(tfReview)) {
            Messenger.showAlertDialog(
                    this,
                    "Add Review",
                    "Please enter your review before submitting.",
                    "Ok"
            ).show();
            return;
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Destination.collectioName)) {
            Destination destination = getIntent().getParcelableExtra(Destination.collectioName);
            Review review = new Review(
                    userAuth.getCurrentUser().getUid(),
                    destination.getDestinationId(),
                    Miner.getString(tfReview),
                    Timestamp.now()
            );
            reviewRepository.addReview(review);
            tfReview.getEditText().setText("");
        }
    }

}