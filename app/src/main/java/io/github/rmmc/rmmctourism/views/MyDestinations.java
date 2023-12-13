package io.github.rmmc.rmmctourism.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.MyDestinationAdapter;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.repository.DestinationRepository;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.DataCallBack;
import io.github.rmmc.rmmctourism.util.DestinationDataCallback;
import io.github.rmmc.rmmctourism.util.OnDestinationDelete;
import io.github.rmmc.rmmctourism.util.OnDestinationUpdate;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class MyDestinations extends AppCompatActivity implements
        WidgetInitializer,
        ActionInitializer {

    // Declare widgets
    private RecyclerView rvMyDestinations;
    private MyDestinationAdapter myDestinationAdapter;
    private DestinationRepository destinationRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_destinations);

        // Initialize DestinationRepository for data operations
        destinationRepository = new DestinationRepository();

        // Initialize widgets and set up actions
        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeActions() {
        // No specific actions initialized in this activity
        // (You can add actions related to RecyclerView item clicks or other interactions)
    }

    @Override
    public void initializeWidgets() {
        // Get the context for later use
        Context context = this;

        // Retrieve data from the repository using a callback
        destinationRepository.getMyDestination(new DestinationDataCallback<Destination>() {
            @Override
            public void onDataLoaded(List<Destination> destinationList) {
                // Create and set up the RecyclerView and its adapter
                myDestinationAdapter = new MyDestinationAdapter(destinationList, context);
                rvMyDestinations = findViewById(R.id.rv_my_destinations);
                rvMyDestinations.setAdapter(myDestinationAdapter);
                rvMyDestinations.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onDataNotAvailable() {
                // Handle the case where data is not available
                // (e.g., show a message to the user)
            }
        });
    }
}
