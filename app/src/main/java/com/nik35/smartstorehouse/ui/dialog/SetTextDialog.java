package com.nik35.smartstorehouse.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.EditText;

import com.nik35.smartstorehouse.R;
import com.nik35.smartstorehouse.ui.draw.DrawTextFragment;

public class SetTextDialog extends Dialog {

    private DrawTextFragment fragment;

    public SetTextDialog(DrawTextFragment fragment) {
        super(fragment.requireContext());

        this.fragment = fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_set_text);

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        findViewById(R.id.set_text_view).getLayoutParams().width = displayMetrics.widthPixels;

        EditText text = findViewById(R.id.text);

        findViewById(R.id.set).setOnClickListener(view -> {
            fragment.setText(text.getText().toString());
            dismiss();
        });
    }
}
