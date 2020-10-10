package com.nik35.smartstorehouse.ui.draw;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nik35.smartstorehouse.R;
import com.nik35.smartstorehouse.ui.BaseFragment;
import com.nik35.smartstorehouse.ui.dialog.SetTextDialog;
import com.nik35.smartstorehouse.utils.DemoBubblesView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import androidx.annotation.Nullable;

public class DrawTextFragment extends BaseFragment {

    private  DemoBubblesView demoBubblesView;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_draw_text;
    }

    public DrawTextFragment() {
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {

        Display display = requireActivity().getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);


        demoBubblesView = $(R.id.draw_view);
        demoBubblesView.setDisplaySize(screenSize);

        $(R.id.save).setOnClickListener(view -> saveImage(demoBubblesView.get(), "temp"));
        $(R.id.set_text).setOnClickListener(view -> openSetTextDialog());

        SeekBar textSize = $(R.id.text_size);
        SeekBar paddingStartEnd = $(R.id.padding_start_end);
        SeekBar paddingTop = $(R.id.padding_top);

        textSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                demoBubblesView.setTextSize(textSize.getProgress() + 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        paddingStartEnd.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                demoBubblesView.setPaddingStartEnd(paddingStartEnd.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        paddingTop.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                demoBubblesView.setPaddingTop(paddingTop.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        $(R.id.prev_date).setOnClickListener(view -> demoBubblesView.setDate(-1, $(R.id.date)));
        $(R.id.next_date).setOnClickListener(view -> demoBubblesView.setDate(1, $(R.id.date)));
    }

    public void setText(String text){
        demoBubblesView.setText(text);
    }

    public void openSetTextDialog(){
        SetTextDialog setTextDialog = new SetTextDialog(this);

        Objects.requireNonNull(setTextDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setTextDialog.setCanceledOnTouchOutside(false);
        setTextDialog.show();
    }

    private void saveImage(Bitmap finalBitmap, String image_name) {

        File file = null;
        try {
            file = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File createImageFile() throws IOException {
        String imageFileName = "test";
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }
}
