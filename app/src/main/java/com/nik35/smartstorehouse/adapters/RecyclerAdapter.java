package com.nik35.smartstorehouse.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nik35.smartstorehouse.R;
import com.nik35.smartstorehouse.utils.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<RecyclerModel> items;
    private Fragment fragment;

    public RecyclerAdapter(Fragment fragment, List<RecyclerModel> items) {
        this.items = items;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        switch (viewType){
            case Constants.CONTAINER_ITEM:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container, parent, false);
                return new ContainerHolder(itemView);
            case Constants.CONTAINER_INSIDE_ITEM:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_inside, parent, false);
                return new ContainerInsideHolder(itemView);
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (items.get(position).getType()) {
            case Constants.CONTAINER_ITEM:
                ContainerHolder containerHolder = (ContainerHolder) viewHolder;
                containerHolder.view.setOnClickListener(view -> Navigation.findNavController(fragment.requireView()).navigate(R.id.containerEditFragment));
                break;
            case Constants.CONTAINER_INSIDE_ITEM:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (items.get(position).getType()) {
            case 0:
                return Constants.CONTAINER_ITEM;
            case 1:
                return Constants.CONTAINER_INSIDE_ITEM;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ContainerHolder extends RecyclerView.ViewHolder {

        CardView view;

        ContainerHolder(View itemView) {
            super(itemView);

            view = itemView.findViewById(R.id.view);
        }
    }

    static class ContainerInsideHolder extends RecyclerView.ViewHolder {

        ContainerInsideHolder(View itemView) {
            super(itemView);
        }
    }
}