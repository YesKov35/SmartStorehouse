package com.nik35.smartstorehouse.data.repository;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nik35.smartstorehouse.data.models.Container;

import java.util.ArrayList;

public class DataRepository {

    private DatabaseReference myRef;
    private Container selectedContainer;

    public DataRepository() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        myRef = FirebaseDatabase
                .getInstance()
                .getReference("anna");
    }

    public DatabaseReference getMyRef() {
        return myRef;
    }

    public void setMyRef(DatabaseReference myRef) {
        this.myRef = myRef;
    }

    public Container getSelectedContainer() {
        return selectedContainer;
    }

    public void setSelectedContainer(Container selectedContainer) {
        this.selectedContainer = selectedContainer;
    }
}
