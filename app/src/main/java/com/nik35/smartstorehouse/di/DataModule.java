package com.nik35.smartstorehouse.di;

import android.content.Context;
import android.util.Log;

import com.nik35.smartstorehouse.data.repository.DataRepository;

import java.util.concurrent.TimeUnit;

import toothpick.config.Module;

public class DataModule extends Module {

    public DataModule(Context context) {
        // Repository
        bind(DataRepository.class).toInstance(new DataRepository());
    }
}
