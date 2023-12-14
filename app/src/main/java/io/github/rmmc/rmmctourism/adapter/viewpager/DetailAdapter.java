/**
 * DetailAdapter is a ViewPager2 adapter responsible for managing the fragments
 * displayed in the detail view of a destination in the RMMC Tourism app.
 * <p>
 * This adapter provides fragments for the "About," "Gallery," and "Reviews" sections,
 * allowing users to navigate through different aspects of a destination.
 *
 * @param fragmentActivity The FragmentActivity that hosts the ViewPager2.
 * @param destination The Destination object associated with the detail view.
 */
package io.github.rmmc.rmmctourism.adapter.viewpager;

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

    /**
     * Constructs a DetailAdapter with a specified FragmentActivity and Destination.
     *
     * @param fragmentActivity The FragmentActivity that hosts the ViewPager2.
     * @param destination The Destination object associated with the detail view.
     */
    public DetailAdapter(@NonNull FragmentActivity fragmentActivity, Destination destination) {
        super(fragmentActivity);
        this.destination = destination;
    }

    /**
     * Constructs a DetailAdapter with a specified FragmentActivity.
     *
     * @param fragmentActivity The FragmentActivity that hosts the ViewPager2.
     */
    public DetailAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    /**
     * Creates and returns the Fragment associated with the specified position.
     *
     * @param position The position of the Fragment within the ViewPager.
     * @return A Fragment instance corresponding to the specified position.
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                return setFragments(new About(), bundle);
            case 1:
                return setFragments(new Gallery(), bundle);
            case 2:
                return setFragments(new Reviews(), bundle);

            default:
                return setFragments(new About(), bundle);
        }
    }

    /**
     * Sets the arguments for the given Fragment and returns it.
     *
     * @param fragment The Fragment to be configured.
     * @param bundle The Bundle containing arguments to be set.
     * @return The configured Fragment with arguments set.
     */
    private Fragment setFragments(Fragment fragment, Bundle bundle) {
        bundle.putParcelable(Destination.collectioName, destination);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Returns the total number of items that can be displayed by the adapter.
     *
     * @return The total number of items.
     */
    @Override
    public int getItemCount() {
        return 3;
    }
}
