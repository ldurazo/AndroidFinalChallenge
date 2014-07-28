package com.example.ldurazo.androidfinalchallenge;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by ldurazo on 7/27/2014 and 2:44 PM.
 */
public class ImageLoaderApp  extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        //Create ImageLoaderConfiguration
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .diskCacheFileCount(150)
                .diskCacheExtraOptions(800, 480, null)
                .writeDebugLogs()
                .build();

        //Initialize ImageLoader with configuration
        ImageLoader.getInstance().init(config);
    }

}
