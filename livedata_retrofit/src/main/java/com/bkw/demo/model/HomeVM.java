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
 * LiveData+Retrofit+ViewModel
 */
public class HomeVM extends ViewModel {

    private MutableLiveData<Boolean> isRefresh = new MutableLiveData<>();

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

    public LiveData<List<BannerVO>> data = Transformations.map(bannerList, new Function<ApiResponse<List<BannerVO>>, List<BannerVO>>() {
        @Override
        public List<BannerVO> apply(ApiResponse<List<BannerVO>> input) {
            return input != null ? input.getData() : null;
        }
    });

    public void loadData(boolean r) {
        isRefresh.setValue(r);
    }
}
