package com.example.it_capstone_archive;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MongoDBAtlasHelper.init(this);
    }
}