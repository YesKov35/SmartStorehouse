package com.nik35.smartstorehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import toothpick.Scope;
import toothpick.Toothpick;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nik35.smartstorehouse.data.repository.DataRepository;
import com.nik35.smartstorehouse.di.Scopes;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    public DataRepository dataRepository;

    static final int REQUEST_TAKE_PHOTO = 1;
    String currentPhotoPath;

    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Scope scope = Toothpick.openScope(Scopes.DATA_SCOPE);
        Toothpick.inject(this, scope);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }

    private File createImageFile() throws IOException {
        String imageFileName = dataRepository.getSelectedContainer().getId();
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.nik35.smartstorehouse",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            StorageReference ref = dataRepository.getmStorageRef().child("images/"+ dataRepository.getSelectedContainer().getId() + ".jpg");

            UploadTask uploadTask = ref.putFile(photoURI);

            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }

                return ref.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    dataRepository.getSelectedContainer().setAvatar(Objects.requireNonNull(downloadUri).toString());
                    dataRepository.getMyRef().child(dataRepository.getSelectedContainer().getId()).setValue(dataRepository.getSelectedContainer());
                } else {
                }
            });
        }
    }

}