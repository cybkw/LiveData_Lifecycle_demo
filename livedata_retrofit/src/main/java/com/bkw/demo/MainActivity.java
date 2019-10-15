package com.bkw.demo;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bkw.demo.api.WanApi;
import com.bkw.demo.bean.ApiResponse;
import com.bkw.demo.bean.BannerVO;
import com.bkw.demo.factory.RetrofitCreateHelper;
import com.bkw.demo.model.HomeVM;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * @author bkw
 */
public class MainActivity extends AppCompatActivity {


    private BGABanner bgaBanner;
    private BGABanner.Adapter<ImageView, BannerVO> adapter;
    private HomeVM homeVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bgaBanner = findViewById(R.id.bannerView);
        homeVM = ViewModelProviders.of(this).get(HomeVM.class);


        adapter = new BGABanner.Adapter<ImageView, BannerVO>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable BannerVO model, int position) {
                Glide.with(MainActivity.this).load(model.getImagePath()).into(itemView);
            }
        };
        bgaBanner.setAdapter(adapter);
        homeVM.data.observe(this, new Observer<List<BannerVO>>() {
            @Override
            public void onChanged(@Nullable List<BannerVO> bannerVOS) {
                bgaBanner.setData(bannerVOS, null);
            }
        });

        init();
    }

    private MutableLiveData<Boolean> isRefresh;

    private void init() {
        isRefresh = new MutableLiveData<>();
        final WanApi api = RetrofitCreateHelper.createApi(WanApi.class, WanApi.URL);

        LiveData<ApiResponse<List<BannerVO>>> bannerList = Transformations.switchMap(isRefresh, new Function<Boolean, LiveData<ApiResponse<List<BannerVO>>>>() {
            @Override
            public LiveData<ApiResponse<List<BannerVO>>> apply(Boolean input) {
                if (input) {
                    return api.bannerList();
                }
                return null;
            }
        });


        //为了展示banner，我们通过map将ApiResponse转换成最终关心的数据是List<BannerVO>
        LiveData<List<BannerVO>> data = Transformations.map(bannerList, new Function<ApiResponse<List<BannerVO>>, List<BannerVO>>() {
            @Override
            public List<BannerVO> apply(ApiResponse<List<BannerVO>> input) {
                return input.getData();
            }
        });

        //监听数据
        data.observe(this, new Observer<List<BannerVO>>() {
            @Override
            public void onChanged(@Nullable List<BannerVO> bannerVOS) {
                Log.e("TAG", bannerVOS.get(0).toString());
            }
        });
    }

    /**
     * LiveData+Retrofit请求网络数据
     *
     * @param view
     */
    public void test(View view) {

        RetrofitCreateHelper.createApi(WanApi.class, WanApi.URL)
                .bannerList().observe(this, new Observer<ApiResponse<List<BannerVO>>>() {
            @Override
            public void onChanged(@Nullable ApiResponse<List<BannerVO>> listApiResponse) {
                Log.e("TAG", listApiResponse.getData().get(0).toString());
            }
        });
    }


    /**
     * 根据refresh的状态来表示是否更新请求banner数据
     *
     * @param view
     */
    public void test2(View view) {
        isRefresh.setValue(true);
    }

    /**
     * LiveData+Retrofit+ViewModel
     *
     * @param view
     */
    public void test3(View view) {
        homeVM.loadData(true);
    }

    /**
     *
     * */
}
