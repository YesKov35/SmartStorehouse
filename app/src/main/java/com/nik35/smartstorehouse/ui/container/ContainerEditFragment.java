package com.nik35.smartstorehouse.ui.container;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ContainerEditFragment extends BaseFragment {

    private Container container;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_container_edit;
    }

    public ContainerEditFragment() {
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {

        EditText name = $(R.id.name);
        EditText itemName = $(R.id.item_name);
        RecyclerView editRecycler = $(R.id.edit_recycler);

        List<RecyclerModel> recyclerModels = new ArrayList<>();

        RecyclerAdapter adapter = new RecyclerAdapter(this, recyclerModels);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        editRecycler.setLayoutManager(manager);
        editRecycler.setAdapter(adapter);

        if(dataRepository.getSelectedContainer() != null) {
            container = dataRepository.getSelectedContainer();

            dataRepository.getMyRef().child(container.getId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    recyclerModels.clear();
                    container = dataSnapshot.getValue(Container.class);
                    if(container != null){
                        container.setId(dataSnapshot.getKey());

                        if(container.getItems() != null) {
                            for (String name : container.getItems()) {
                                recyclerModels.add(new RecyclerModel(Constants.CONTAINER_INSIDE_ITEM, name));
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }else{

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            name.setText(container.getName());
            name.setOnEditorActionListener((v, actionId, event) -> {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    if (name.getText().toString().isEmpty()) {
                        name.setText("Контейнер");
                    }
                    container.setName(name.getText().toString());
                    dataRepository.getMyRef().child(container.getId()).setValue(container);
                    closeKeyboard();
                    return true;
                }
                return false;
            });

            itemName.setOnEditorActionListener((v, actionId, event) -> {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    if (!itemName.getText().toString().isEmpty()) {
                        if(container.getItems() == null){
                            container.setItems(new ArrayList<>());
                        }
                        container.getItems().add(itemName.getText().toString());
                        dataRepository.getMyRef().child(container.getId()).setValue(container);
                        itemName.setText("");
                        closeKeyboard();
                    }else{
                        Toast.makeText(getContext(), "Любимая введи название!", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            });
        }
    }

    public void deleteItem(int position){
        container.getItems().remove(position);
        dataRepository.getMyRef().child(container.getId()).setValue(container);
    }
}
