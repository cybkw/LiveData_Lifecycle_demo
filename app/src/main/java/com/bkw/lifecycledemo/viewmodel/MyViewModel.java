package com.bkw.lifecycledemo.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * 自定义ViewModel
 *
 * @author bkw
 */
public class MyViewModel extends ViewModel {
    private MutableLiveData<String> name;

    /**
     * getName方法中创建一个MutableLiveData,并通过setValue方法来更新数据
     *
     * @return
     */
    public MutableLiveData<String> getName() {
        if (null == name) {
            name = new MutableLiveData<>();
            addName();
        }
        return name;
    }

    private void addName() {
        name.setValue("ViewModel + LiveData");
    }

    public void changeName() {
        name.setValue("我发生了改变");
    }
}
