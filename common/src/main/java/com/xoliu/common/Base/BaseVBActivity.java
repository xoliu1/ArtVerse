package com.xoliu.common.Base;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseVBActivity<VB extends ViewBinding> extends BaseActivity {

    protected VB binding;

    /***
     * 构造ViewBinding
     *
     * @param savedInstanceState
     * @return void
     * @author xoliu
     * @create 23-11-9
     **/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            try {
                Class<VB> clazz = (Class<VB>) ((ParameterizedType) type).getActualTypeArguments()[0];
                //反射
                Method method = clazz.getMethod("inflate", LayoutInflater.class);
                binding = (VB) method.invoke(null, getLayoutInflater());
            } catch (Exception e) {
                e.printStackTrace();
            }
            setContentView(binding.getRoot());
        }
        initData();
        initView();
    }



    /***
     * 初始化数据
     *
     * @return void
     * @author xoliu
     * @create 23-11-9
     **/

    protected abstract void initData();

    /***
     * 初始化UI
     *
     * @return void
     * @author xoliu
     * @create 23-11-9
     **/
    protected abstract void initView();
}

