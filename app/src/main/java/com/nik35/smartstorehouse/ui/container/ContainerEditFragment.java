package com.nik35.smartstorehouse.ui.container;

import android.os.Bundle;

import com.nik35.smartstorehouse.R;
import com.nik35.smartstorehouse.adapters.RecyclerAdapter;
import com.nik35.smartstorehouse.adapters.RecyclerModel;
import com.nik35.smartstorehouse.ui.BaseFragment;
import com.nik35.smartstorehouse.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ContainerEditFragment extends BaseFragment {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_container_edit;
    }

    public ContainerEditFragment() {
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {

        List<RecyclerModel> recyclerModels = new ArrayList<>();

        recyclerModels.add(new RecyclerModel(Constants.CONTAINER_INSIDE_ITEM));
        recyclerModels.add(new RecyclerModel(Constants.CONTAINER_INSIDE_ITEM));
        recyclerModels.add(new RecyclerModel(Constants.CONTAINER_INSIDE_ITEM));
        recyclerModels.add(new RecyclerModel(Constants.CONTAINER_INSIDE_ITEM));
        recyclerModels.add(new RecyclerModel(Constants.CONTAINER_INSIDE_ITEM));

        RecyclerView editRecycler = $(R.id.edit_recycler);

        RecyclerAdapter adapter = new RecyclerAdapter(this, recyclerModels);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        editRecycler.setLayoutManager(manager);
        editRecycler.setAdapter(adapter);
    }
}
