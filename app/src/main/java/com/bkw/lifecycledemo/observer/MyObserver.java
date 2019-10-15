package com.bkw.lifecycledemo.observer;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * @author bkw
 */
public class MyObserver implements DefaultLifecycleObserver {

    private static final String TAG = "MyObserver";

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "Lifecycle onCreate");
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "Lifecycle onStart");
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "Lifecycle ON_RESUME");
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "Lifecycle- ON_PAUSE");
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "Lifecycle- ON_STOP");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "Lifecycle- onDestroy");
    }
}
