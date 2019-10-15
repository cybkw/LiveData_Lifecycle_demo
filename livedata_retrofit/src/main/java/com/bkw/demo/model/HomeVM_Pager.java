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

/*** 分页请求加载
 * * LiveData+Retrofit+ViewModel+SmartRefreshLayout
 * <p>
 * 加入page,refreshing,moreLoading，hasMore变量控制分页请求
 用SmartRefreshLayout作为分页组件
 */
public class HomeVM_Pager extends ViewModel {

    //页码
    MutableLiveData<Integer> page = new MutableLiveData<>();
    //下拉刷新状态
    MutableLiveData<Boolean> isRefresh = new MutableLiveData<>();
    //上拉加载更多状态
    MutableLiveData<Boolean> moreLoading = new MutableLiveData<>();
    //是否还有更多数据
    MutableLiveData<Boolean> hasMore = new MutableLiveData<>();


    public WanApi wanApi = RetrofitCreateHelper.createApi(WanApi.class, WanApi.URL);

    public LiveData<ApiResponse<List<BannerVO>>> articleList = Transformations.switchMap(page, new Function<Integer, LiveData<ApiResponse<List<BannerVO>>>>() {
        @Override
        public LiveData<ApiResponse<List<BannerVO>>> apply(Integer input) {
            return wanApi.articleList(input);
        }
    });

    public LiveData<List<BannerVO>> data = Transformations.map(articleList, new Function<ApiResponse<List<BannerVO>>, List<BannerVO>>() {
        @Override
        public List<BannerVO> apply(ApiResponse<List<BannerVO>> input) {
            isRefresh.setValue(false);
            moreLoading.setValue(false);
            if (input != null) {
                hasMore.setValue(true);
            }
            hasMore.setValue(false);
//            hasMore.setValue(input != null ?: false);
            return input != null ? input.getData() : null;
        }
    });

    public void loadMore() {
        if (page.getValue() != 0) {
            page.setValue(page.getValue() + 1);
        }

        page.setValue(0);
        moreLoading.setValue(true);
    }

    public void refresh() {
        page.setValue(0);
        isRefresh.setValue(true);
    }
}
