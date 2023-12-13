package io.github.rmmc.rmmctourism.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.rmmc.rmmctourism.R;
import io.github.rmmc.rmmctourism.adapter.ExploreAdapter;
import io.github.rmmc.rmmctourism.model.Destination;
import io.github.rmmc.rmmctourism.model.Favorite;
import io.github.rmmc.rmmctourism.model.UserInformation;
import io.github.rmmc.rmmctourism.repository.DestinationRepository;
import io.github.rmmc.rmmctourism.repository.FavoriteRepository;
import io.github.rmmc.rmmctourism.repository.UserRepository;
import io.github.rmmc.rmmctourism.util.DataCallBack;
import io.github.rmmc.rmmctourism.util.DestinationDataCallback;
import io.github.rmmc.rmmctourism.util.OnFavoriteDataCallback;
import io.github.rmmc.rmmctourism.util.WidgetInitializer;

/**
 * Fragment for displaying the dashboard with destination cards and user information.
 */
public class Dashboard extends Fragment {

    private RecyclerView rvDashboard;
    private ExploreAdapter exploreAdapter;
    private DestinationRepository destinationRepository;
    private FavoriteRepository favoriteRepository;

    private UserRepository userRepository;
    private TextView tvFirstName;
    private SnapHelper snapHelper;

    /**
     * Called when the fragment is first created.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        destinationRepository = new DestinationRepository();
        userRepository = new UserRepository(getContext());
        favoriteRepository = new FavoriteRepository(getContext());
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        snapHelper = new LinearSnapHelper();
        rvDashboard = view.findViewById(R.id.rv_dashboard_destination);
        snapHelper.attachToRecyclerView(rvDashboard);
        tvFirstName = view.findViewById(R.id.tv_dashboard_first_name);
        populateData();
        return view;
    }

    /**
     * Populates the dashboard with destination cards and user information.
     */
    private void populateData() {
        destinationRepository.getDestination(new DestinationDataCallback<Destination>() {
            @Override
            public void onDataLoaded(List<Destination> destinations) {
                favoriteRepository.getFavorite(new OnFavoriteDataCallback() {
                    @Override
                    public void onSuccess(List<Favorite> favorites) {
                        exploreAdapter = new ExploreAdapter(getContext(), destinations, favorites);
                        rvDashboard.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
                        rvDashboard.setAdapter(exploreAdapter);
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        exploreAdapter = new ExploreAdapter(getContext(), destinations);
                        rvDashboard.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
                        rvDashboard.setAdapter(exploreAdapter);
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {
                // Handle when destination data is not available
            }
        });

        userRepository.getUserInformation(new DataCallBack<UserInformation>() {
            @Override
            public void onDataLoaded(UserInformation userInformation) {
                tvFirstName.setText(userInformation.getFirstName());
                Log.d(TAG, "Name: " + userInformation.getFirstName());
            }

            @Override
            public void onDataNotAvailable(String error) {
                Log.d(TAG, "Name error " + error);
            }
        });
    }
}
