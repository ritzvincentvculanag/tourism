package io.github.rmmc.rmmctourism.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class SpinnerAdapter<T> {

    /**
     * Get an ArrayAdapter for a Spinner.
     *
     * @param context The context of the calling activity or fragment.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param list The List of items to be displayed in the spinner.
     * @return An ArrayAdapter for the specified Spinner.
     */
        public ArrayAdapter<T> GetArrayAdapter(Context context, int id, List<T> list){
            return new ArrayAdapter<>(context, id, list);
        }
}
