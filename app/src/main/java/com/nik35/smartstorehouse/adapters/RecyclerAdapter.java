package com.nik35.smartstorehouse.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nik35.smartstorehouse.R;
import com.nik35.smartstorehouse.data.models.Container;
import com.nik35.smartstorehouse.ui.container.ContainerEditFragment;
import com.nik35.smartstorehouse.ui.home.HomeFragment;
import com.nik35.smartstorehouse.utils.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
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
            case Constants.GALLERY_ITEM:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
                return new ContainerHolder(itemView);
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (items.get(position).getType()) {
            case Constants.CONTAINER_ITEM:
                ContainerHolder containerHolder = (ContainerHolder) viewHolder;
                Container container = items.get(position).getContainer();

                containerHolder.view.setOnClickListener(view -> ((HomeFragment) fragment).selectedContainer(container));
                containerHolder.name.setText(container.getName());

                if(container.getAvatar() != null && !container.getAvatar().isEmpty()) {
                    Uri uri = Uri.parse(container.getAvatar());
                    Glide.with(fragment).load(uri).into(containerHolder.image);
                }
                break;
            case Constants.CONTAINER_INSIDE_ITEM:
                ContainerInsideHolder containerInsideHolder = (ContainerInsideHolder) viewHolder;

                containerInsideHolder.name.setText(items.get(position).getItemName());
                containerInsideHolder.delete.setOnClickListener(view -> ((ContainerEditFragment) fragment).deleteItem(position));
                break;
            case Constants.GALLERY_ITEM:
                ContainerHolder galleryHolder = (ContainerHolder) viewHolder;
                String img = items.get(position).getItemName();

                if(img != null && !img.isEmpty()) {
                    Glide.with(fragment).load(img).into(galleryHolder.image);
                }
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
            case 2:
                return Constants.GALLERY_ITEM;
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
        TextView name;
        ImageView image;
        ImageView delete;
        ImageView share;

        ContainerHolder(View itemView) {
            super(itemView);

            view = itemView.findViewById(R.id.view);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            delete = itemView.findViewById(R.id.delete);
            share = itemView.findViewById(R.id.share);
        }
    }

    static class ContainerInsideHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView delete;

        ContainerInsideHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
