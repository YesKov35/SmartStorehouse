package com.nik35.smartstorehouse.ui.home;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nik35.smartstorehouse.R;
import com.nik35.smartstorehouse.adapters.RecyclerAdapter;
import com.nik35.smartstorehouse.adapters.RecyclerModel;
import com.nik35.smartstorehouse.data.models.Container;
import com.nik35.smartstorehouse.ui.BaseFragment;
import com.nik35.smartstorehouse.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
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

        RecyclerView homeRecycler = $(R.id.home_recycler);

        RecyclerAdapter adapter = new RecyclerAdapter(this, recyclerModels);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        homeRecycler.setLayoutManager(manager);
        homeRecycler.setAdapter(adapter);

        FloatingActionButton addContainer = $(R.id.add_container);

        addContainer.setOnClickListener(view -> {
            Container container = new Container();
            container.setName("Контейнер");
            dataRepository.getMyRef().push().setValue(container);
        });

        dataRepository.getMyRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recyclerModels.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Container container = postSnapshot.getValue(Container.class);
                    if(container != null) {
                        container.setId(postSnapshot.getKey());
                        recyclerModels.add(new RecyclerModel(Constants.CONTAINER_ITEM, container));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        homeRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    addContainer.hide();
                else
                    addContainer.show();
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    public void selectedContainer(Container container){
        dataRepository.setSelectedContainer(container);
        Navigation.findNavController(requireView()).navigate(R.id.containerEditFragment);
    }
}
