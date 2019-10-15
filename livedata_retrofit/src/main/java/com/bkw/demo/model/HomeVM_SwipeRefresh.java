package com.bkw.demo.model;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.bkw.demo.api.WanApi;
import com.bkw.demo.bean.ApiResponse;
import com.bkw.demo.bean.BannerVO;
import com.bkw.demo.factory.RetrofitCreateHelper;

import java.util.List;

/**
 *  * LiveData+Retrofit+ViewModel+SwipeRefreshLayout
 *
 * 为了控制加载进度条显示隐藏，我们在HomeVM中添加loading变量，
 * 在调用loadData时通过loading.value=true控制进度条的显示，在map中的转换函数中控制进度的隐藏

 */
public class HomeVM_SwipeRefresh extends ViewModel {

    MutableLiveData<Boolean> isRefresh = new MutableLiveData<>();
    MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private WanApi wanApi = RetrofitCreateHelper.createApi(WanApi.class, WanApi.URL);

    private LiveData<ApiResponse<List<BannerVO>>> bannerList = Transformations.switchMap(isRefresh, new Function<Boolean, LiveData<ApiResponse<List<BannerVO>>>>() {
        @Override
        public LiveData<ApiResponse<List<BannerVO>>> apply(Boolean input) {
            if (input) {
                return wanApi.bannerList();
            }
            return null;
        }
    });

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<List<BannerVO>> data = Transformations.map(bannerList, new Function<ApiResponse<List<BannerVO>>, List<BannerVO>>() {
        @Override
        public List<BannerVO> apply(ApiResponse<List<BannerVO>> input) {
            loading.setValue(false);
            return input != null ? input.getData() : null;
        }
    });

    public void loadData() {
        isRefresh.setValue(true);
        loading.setValue(true);
    }
}
