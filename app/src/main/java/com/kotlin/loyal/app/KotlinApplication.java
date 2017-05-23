package com.kotlin.loyal.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class KotlinApplication extends Application {
    private static KotlinApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Fresco.initialize(this);
    }

    public static KotlinApplication getApplication() {
        return application;
    }
}