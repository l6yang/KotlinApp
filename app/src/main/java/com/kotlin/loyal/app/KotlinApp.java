package com.kotlin.loyal.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class KotlinApp extends Application {
    private static KotlinApp application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Fresco.initialize(this);
    }

    public static KotlinApp getApplication() {
        return application;
    }
}