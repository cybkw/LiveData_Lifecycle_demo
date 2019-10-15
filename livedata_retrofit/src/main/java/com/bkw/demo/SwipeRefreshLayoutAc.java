package com.bkw.demo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bkw.demo.bean.BannerVO;
import com.bkw.demo.model.HomeVM_SwipeRefresh;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * @author bkw
 */
public class SwipeRefreshLayoutAc extends AppCompatActivity {


    private BGABanner bgaBanner;
    private BGABanner.Adapter<ImageView, BannerVO> adapter;
    private HomeVM_SwipeRefresh homeVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main_viewmodel_2);
        homeVM = ViewModelProviders.of(this).get(HomeVM_SwipeRefresh.class);
        binding.setLifecycleOwner(this);


        bgaBanner = findViewById(R.id.bannerView);

        binding.executePendingBindings();

        adapter = new BGABanner.Adapter<ImageView, BannerVO>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable BannerVO model, int position) {
                Glide.with(SwipeRefreshLayoutAc.this).load(model.getImagePath()).into(itemView);
            }
        };
        bgaBanner.setAdapter(adapter);
        homeVM.data.observe(this, new Observer<List<BannerVO>>() {
            @Override
            public void onChanged(@Nullable List<BannerVO> bannerVOS) {
                bgaBanner.setData(bannerVOS, null);
            }
        });

    }


    /**
     * LiveData+Retrofit请求网络数据
     *
     * @param view
     */
    public void test(View view) {

//        RetrofitCreateHelper.createApi(WanApi.class, WanApi.URL)
//                .bannerList().observe(this, new Observer<ApiResponse<List<BannerVO>>>() {
//            @Override
//            public void onChanged(@Nullable ApiResponse<List<BannerVO>> listApiResponse) {
//                Log.e("TAG", listApiResponse.getData().get(0).toString());
//            }
//        });
    }


    /**
     * 根据refresh的状态来表示是否更新请求banner数据
     *
     * @param view
     */
    public void test2(View view) {
//        isRefresh.setValue(true);
        homeVM.loadData();
    }

    /**
     * LiveData+Retrofit+ViewModel
     *
     * @param view
     */
    public void test3(View view) {

    }

}
