package io.github.rmmc.rmmctourism.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.ExploreSearchAdapter;
import io.github.rmmc.rmmctourism.util.ActionInitializer;
import io.github.rmmc.rmmctourism.util.OnDestinationClick;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

public class Favorites extends Fragment implements WidgetInitializer, ActionInitializer, OnDestinationClick {

    private View view;

    private ExploreSearchAdapter adapter;
    private RecyclerView favorites;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);

        initializeWidgets();
        initializeWidgets();

        return view;
    }

    @Override
    public void initializeWidgets() {
        adapter = new ExploreSearchAdapter(getContext(), new ArrayList<>());
        favorites = view.findViewById(R.id.rv_favorites);
        favorites.setAdapter(adapter);
        favorites.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initializeActions() {
        // TODO: Ikaw na bahala ano gawin mo dito
    }

    @Override
    public void onDestinationClick(int position) {
        // TODO: implement on view destination click
    }

}