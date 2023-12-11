package io.github.rmmc.rmmctourism.adapter.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import io.github.rmmc.rmmctourism.fragments.About;
import io.github.rmmc.rmmctourism.fragments.Gallery;
import io.github.rmmc.rmmctourism.fragments.Reviews;

public class DetailAdapter extends FragmentStateAdapter {

    public DetailAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new About();
            case 1: return new Gallery();
            case 2: return new Reviews();

            default: return new About();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
