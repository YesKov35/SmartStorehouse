package com.nik35.smartstorehouse.ui.home;

import android.os.Bundle;
import com.nik35.smartstorehouse.R;
import com.nik35.smartstorehouse.ui.BaseFragment;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

public class HomeFragment extends BaseFragment {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    public HomeFragment() {
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        $(R.id.nav_to_edit).setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.containerEditFragment));
    }
}
