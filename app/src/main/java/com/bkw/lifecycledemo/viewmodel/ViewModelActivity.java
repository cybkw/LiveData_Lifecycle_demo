package com.bkw.lifecycledemo.viewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bkw.lifecycledemo.R;

public class ViewModelActivity extends AppCompatActivity {

    Button button;

    MyViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_viewmodel);

        button = findViewById(R.id.button1);


        model = ViewModelProviders.of(this).get(MyViewModel.class);
        model.getName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.e("TAG", "本Demo:" + s);
                button.setText(s);

            }
        });

    }

    public void test1(View view) {
        model.changeName();
    }
}
