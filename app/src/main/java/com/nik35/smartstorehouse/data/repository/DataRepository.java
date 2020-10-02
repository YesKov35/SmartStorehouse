package com.nik35.smartstorehouse.data.repository;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nik35.smartstorehouse.data.models.Container;

import java.util.ArrayList;

public class DataRepository {

    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private Container selectedContainer;

    public DataRepository() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        myRef = FirebaseDatabase
                .getInstance()
                .getReference("anna");

        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public DatabaseReference getMyRef() {
        return myRef;
    }

    public Container getSelectedContainer() {
        return selectedContainer;
    }

    public void setSelectedContainer(Container selectedContainer) {
        this.selectedContainer = selectedContainer;
    }

    public StorageReference getmStorageRef() {
        return mStorageRef;
    }
}
