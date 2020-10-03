package com.nik35.smartstorehouse.ui.draw;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.widget.SeekBar;

import com.nik35.smartstorehouse.R;
import com.nik35.smartstorehouse.ui.BaseFragment;
import com.nik35.smartstorehouse.utils.DemoBubblesView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.annotation.Nullable;

public class DrawTextFragment extends BaseFragment {

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


        DemoBubblesView demoBubblesView = $(R.id.draw_view);
        demoBubblesView.setDisplaySize(screenSize);

        $(R.id.save).setOnClickListener(view -> saveImage(demoBubblesView.get(), "temp"));

        SeekBar textSize = $(R.id.text_size);

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