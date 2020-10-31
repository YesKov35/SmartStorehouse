package com.nik35.smartstorehouse.ui.draw;

import android.os.Bundle;
import android.os.Environment;

import com.nik35.smartstorehouse.R;
import com.nik35.smartstorehouse.adapters.RecyclerAdapter;
import com.nik35.smartstorehouse.adapters.RecyclerModel;
import com.nik35.smartstorehouse.data.models.Container;
import com.nik35.smartstorehouse.ui.BaseFragment;
import com.nik35.smartstorehouse.utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryFragment extends BaseFragment {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_gallery;
    }

    public GalleryFragment() {
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {

        List<RecyclerModel> recyclerModels = new ArrayList<>();

        RecyclerView homeRecycler = $(R.id.gallery_recycler);

        String[] images = showImageList();

        if(images != null){
            String path = Objects.requireNonNull(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)).getAbsolutePath();

            for(String img : images){
                recyclerModels.add(new RecyclerModel(Constants.GALLERY_ITEM, path + "/" + img));
            }

            RecyclerAdapter adapter = new RecyclerAdapter(this, recyclerModels);
            GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
            homeRecycler.setLayoutManager(manager);
            homeRecycler.setAdapter(adapter);
        }
    }

    private String[] showImageList() {
        String[] arrayList = new String[0];
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (storageDir != null && storageDir.exists()) {
            String[] fileListImages = storageDir.list();
            if (fileListImages != null) {
                return fileListImages;
            }
        }

        return null;
    }
}
