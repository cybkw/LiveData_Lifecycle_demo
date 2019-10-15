package com.bkw.lifecycledemo;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LiveDataAc extends AppCompatActivity {
    MutableLiveData<String> mutableLiveData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_livedata);

        Button button = findViewById(R.id.button1);

        mutableLiveData = new MutableLiveData<>();
        mutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("TAG", "onChanged" + s);
            }
        });

        // Transformations.map 类似于RxJava的map转换操作符
        LiveData map = Transformations.map(mutableLiveData, new Function<String, Object>() {
            @Override
            public Object apply(String input) {
                return null;
            }
        });

        //Transformations.switchMap类似于flatMap转换操作，同样返回的是对象
        LiveData yLiveData = Transformations.switchMap(mutableLiveData, new Function<String, LiveData<String>>() {
            @Override
            public LiveData<String> apply(String input) {
                MutableLiveData mutableLiveData1 = new MutableLiveData();
                return mutableLiveData1;
            }
        });

        //
        map.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {

            }
        });


    }

    public void test(View view) {
        mutableLiveData.setValue("发生了改变");
    }
}
