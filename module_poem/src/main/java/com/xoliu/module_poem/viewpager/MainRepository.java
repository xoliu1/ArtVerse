package com.xoliu.module_poem.viewpager;


import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.xoliu.func_network.BaseObserver;
import com.xoliu.func_network.NetworkApi;
import com.xoliu.module_poem.comment.CommentService;

import retrofit2.Callback;

/***
 * 诗句页面的Main仓库
 * @return
 * @author xoliu
 * @create 23-11-21
 **/

public class MainRepository {





    /***
     * 请求card的上层图片
     * @return androidx.lifecycle.MutableLiveData<com.xoliu.module_poem.viewpager.card_picBean>
     * @author xoliu
     * @create 23-11-21
     **/

    @SuppressLint("CheckResult")
    public MutableLiveData<card_picBean> getCardPic() {
        MutableLiveData<card_picBean> cardPic = new MutableLiveData<>();
        CardPicService cardPicService = NetworkApi.createService(CardPicService.class);
        cardPicService.getCardPic().compose(NetworkApi.applySchedulers(new BaseObserver<card_picBean>() {
            @Override
            public void onSuccess(card_picBean cardPicBean) {
                cardPic.setValue(cardPicBean);
                Log.d("TAG", "onSuccess: 加载成功" + cardPicBean.getImgurl());
            }

            @Override
            public void onFailure(Throwable e) {
                Log.d("getCardPic()", "onFailure: 加载失败！");
            }

        }));

        return cardPic;
    }


    @SuppressLint("CheckResult")
    public MutableLiveData<card_picBean> getuserIcon(){
        MutableLiveData<card_picBean> userIcon = new MutableLiveData<>();
        CommentService commentService = NetworkApi.createService(CommentService.class);
        commentService.getUserPic().compose(NetworkApi.applySchedulers(new BaseObserver<card_picBean>() {
            @Override
            public void onSuccess(card_picBean cardPicBean) {
                userIcon.postValue(cardPicBean);
                Log.d("访问头像", "onSuccess: url = " + cardPicBean.getImgurl());
            }

            @Override
            public void onFailure(Throwable e) {
                Log.d("getCardPic()", "onFailure: 加载失败！");
            }

        }));

        return userIcon;
    }

}
