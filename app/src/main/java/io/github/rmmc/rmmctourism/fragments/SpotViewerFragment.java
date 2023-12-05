package io.github.rmmc.rmmctourism.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.DestinationAdapter;
import io.github.rmmc.rmmctourism.model.Destination;


public class SpotViewerFragment extends Fragment {

    private RecyclerView rvSpotViewer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_spot_viewer, container, false);

        rvSpotViewer = view.findViewById(R.id.rv_spot_viewer);


        List<Destination> list = new ArrayList<>();

        list.add(new Destination("dasdad"));
        list.add(new Destination("dasdad"));
        list.add(new Destination("dasdad"));
        list.add(new Destination("dasdad"));
        list.add(new Destination("dasdad"));list.add(new Destination("dasdad"));


        DestinationAdapter adapter = new DestinationAdapter(getContext(), list);

        rvSpotViewer.setAdapter(adapter);
        rvSpotViewer.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }
}