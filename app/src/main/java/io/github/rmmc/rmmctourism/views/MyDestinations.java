package io.github.rmmc.rmmctourism.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.MyDestinationAdapter;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.OnDestinationDelete;
import io.github.rmmc.rmmctourism.util.OnDestinationUpdate;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class MyDestinations extends AppCompatActivity implements
        WidgetInitializer,
        ActionInitializer,
        OnDestinationUpdate,
        OnDestinationDelete {

    private RecyclerView rvMyDestinations;
    private MyDestinationAdapter myDestinationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_destinations);

        initializeWidgets();
        initializeActions();
    }

    @Override
    public void initializeActions() {

    }

    @Override
    public void initializeWidgets() {
        myDestinationAdapter = new MyDestinationAdapter(this, this);
        rvMyDestinations = findViewById(R.id.rv_my_destinations);
        rvMyDestinations.setAdapter(myDestinationAdapter);
        rvMyDestinations.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void delete(int position) {
        // TODO: Handle delete event click
    }

    @Override
    public void update(int position) {
        // TODO: Handle update event click
    }
}