package com.bkw.lifecycledemo;

import android.os.Bundle;
import android.util.Log;

import com.bkw.lifecycledemo.observer.IPresenter;

/**
 * 仿Mvp模式中的P层,加入Lifecycle能监听到Activity的生命周期
 *
 * @author bkw
 */
public class MyPresenter implements IPresenter {


    private static final String TAG = "MyPresenter";

    @Override
    public void onStart() {
        Log.e(TAG, "Lifecycle onStart");
    }

    @Override
    public void onPause() {
        Log.e(TAG, "Lifecycle- ON_PAUSE");
    }


}
