package io.github.rmmc.rmmctourism.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class SpinnerAdapter<T> {
    public ArrayAdapter<T> GetArrayAdapter(Context context, int id, List<T> list){
        return new ArrayAdapter<>(context, id, list);
    }
}