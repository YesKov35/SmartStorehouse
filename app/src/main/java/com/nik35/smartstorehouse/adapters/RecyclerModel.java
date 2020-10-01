package com.nik35.smartstorehouse.adapters;

import com.nik35.smartstorehouse.data.models.Container;

public class RecyclerModel {

    private int type;

    private Container container;
    private String itemName;

    public RecyclerModel(int type){
        this.type = type;
    }

    public RecyclerModel(int type, Container container){
        this.type = type;
        this.container = container;
    }

    public RecyclerModel(int type, String itemName){
        this.type = type;
        this.itemName = itemName;
    }

    public int getType() {
        return type;
    }

    public Container getContainer() {
        return container;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
