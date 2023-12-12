package io.github.rmmc.rmmctourism.adapter.viewpager;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import io.github.rmmc.rmmctourism.fragments.About;
import io.github.rmmc.rmmctourism.fragments.Gallery;
import io.github.rmmc.rmmctourism.fragments.Reviews;
import io.github.rmmc.rmmctourism.model.Destination;

public class DetailAdapter extends FragmentStateAdapter {

    private Destination destination;
    public DetailAdapter(@NonNull FragmentActivity  fragmentActivity, Destination destination){
        super(fragmentActivity);
        this.destination = destination;
    }
    public DetailAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
            case 0: return setFragments(new About(), bundle);
            case 1: return setFragments(new Gallery(), bundle);
            case 2: return setFragments(new Reviews(), bundle);

            default: return setFragments(new About(),bundle);
        }
    }

    private Fragment setFragments(Fragment fragment, Bundle bundle){
        bundle.putParcelable(Destination.collectioName, destination);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
