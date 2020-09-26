package com.nik35.smartstorehouse.ui.home;

import android.os.Bundle;
import com.nik35.smartstorehouse.R;
import com.nik35.smartstorehouse.adapters.RecyclerAdapter;
import com.nik35.smartstorehouse.adapters.RecyclerModel;
import com.nik35.smartstorehouse.ui.BaseFragment;
import com.nik35.smartstorehouse.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends BaseFragment {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    public HomeFragment() {
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {

        List<RecyclerModel> recyclerModels = new ArrayList<>();

        recyclerModels.add(new RecyclerModel(Constants.CONTAINER_ITEM));
        recyclerModels.add(new RecyclerModel(Constants.CONTAINER_ITEM));
        recyclerModels.add(new RecyclerModel(Constants.CONTAINER_ITEM));
        recyclerModels.add(new RecyclerModel(Constants.CONTAINER_ITEM));
        recyclerModels.add(new RecyclerModel(Constants.CONTAINER_ITEM));

        RecyclerView homeRecycler = $(R.id.home_recycler);

        RecyclerAdapter adapter = new RecyclerAdapter(this, recyclerModels);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        homeRecycler.setLayoutManager(manager);
        homeRecycler.setAdapter(adapter);
    }
}
