package com.figueroaluis.finalproject271;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by luisfigueroa on 4/14/18.
 */

public class MainPageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.tasks_list, container, false);
        // include method to setup the recycler view
        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        // set he adapter
    }

}
