package com.nik35.smartstorehouse.ui.container;

import android.os.Bundle;

import com.nik35.smartstorehouse.R;
import com.nik35.smartstorehouse.ui.BaseFragment;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

public class ContainerEditFragment extends BaseFragment {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_container_edit;
    }

    public ContainerEditFragment() {
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        $(R.id.nav_to_home).setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.homeFragment));
    }
}
