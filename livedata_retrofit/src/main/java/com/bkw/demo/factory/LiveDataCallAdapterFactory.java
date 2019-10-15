package com.bkw.demo.factory;

import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import com.bkw.demo.bean.ApiResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != LiveData.class) {
            return null;
        }
        //获取第一个泛型类型
        Type parameterUpperBound = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawType = getRawType(parameterUpperBound);
        if (rawType != ApiResponse.class) {
            throw new IllegalArgumentException("type must be ApiResponse");
        }

        if (!(parameterUpperBound instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be ParameterizedType");
        }
        return new LiveDataCallAdapter<Object>(parameterUpperBound);

    }
}
