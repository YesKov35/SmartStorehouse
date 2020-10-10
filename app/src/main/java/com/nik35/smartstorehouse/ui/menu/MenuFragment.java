package com.nik35.smartstorehouse.ui.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nik35.smartstorehouse.R;
import com.nik35.smartstorehouse.ui.BaseFragment;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.Navigation;

public class MenuFragment extends BaseFragment {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_menu;
    }

    public MenuFragment() {
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {

        View containers = $(R.id.containers);
        View draw = $(R.id.draw);

        ((TextView)containers.findViewById(R.id.name)).setText("Вещи");
        containers.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.homeFragment));

        ((TextView)draw.findViewById(R.id.name)).setText("Текст");
        ((ImageView)draw.findViewById(R.id.image)).setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.bg, null));
        draw.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.drawTextFragment));
    }
}
