package com.bkw.demo.api;

import android.arch.lifecycle.LiveData;

import com.bkw.demo.bean.ApiResponse;
import com.bkw.demo.bean.BannerVO;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WanApi {

    public static String URL = "https://www.wanandroid.com/";

    /**
     * 首页banner
     */
    @GET("banner/json")
    LiveData<ApiResponse<List<BannerVO>>> bannerList();

    /**
     * 首页文章列表
     * */
    @GET("https://www.wanandroid.com/article/list/{page}/json")
    LiveData<ApiResponse<List<BannerVO>>> articleList(@Path("page")int page);

}
