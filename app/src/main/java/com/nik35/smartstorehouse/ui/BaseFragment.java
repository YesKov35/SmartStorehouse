package com.nik35.smartstorehouse.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Objects;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.navigation.Navigation;

public abstract class BaseFragment extends AppCompatDialogFragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutRes(), container, false);

        closeKeyboard();
        initView(savedInstanceState);

        return rootView;
    }

    protected abstract @LayoutRes
    int getLayoutRes();

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Navigation.findNavController(requireView()).popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    protected abstract void initView(@Nullable Bundle bundle);

    protected <T extends View> T $(@IdRes int id) {
        return (T) rootView.findViewById(id);
    }

    protected void openKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        view.requestFocus();
        Objects.requireNonNull(imm).showSoftInput(view, 0);
    }

    public void closeKeyboard() {
        if(getActivity() != null) {
            View view = getActivity().getCurrentFocus();
            if (view instanceof EditText) {
                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void onPause() {
        //closeKeyboard();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        closeKeyboard();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
