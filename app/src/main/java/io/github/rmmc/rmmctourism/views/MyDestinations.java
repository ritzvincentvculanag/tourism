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
        ActionInitializer{

    private RecyclerView rvMyDestinations;
    private MyDestinationAdapter myDestinationAdapter;
    private DestinationRepository destinationRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_destinations);
        destinationRepository = new DestinationRepository();
        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeActions() {

    }

    @Override
    public void initializeWidgets() {
        Context context = this;
        destinationRepository.getMyDestination(new DestinationDataCallback<Destination>() {
            @Override
            public void onDataLoaded(List<Destination> t) {
                myDestinationAdapter = new MyDestinationAdapter(t, context);
                rvMyDestinations = findViewById(R.id.rv_my_destinations);
                rvMyDestinations.setAdapter(myDestinationAdapter);
                rvMyDestinations.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onDataNotAvailable() {

            }

        });


    }

}