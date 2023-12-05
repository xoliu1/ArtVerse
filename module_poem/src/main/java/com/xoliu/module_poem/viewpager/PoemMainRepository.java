package com.xoliu.module_poem.viewpager;


import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.xoliu.func_network.BaseObserver;
import com.xoliu.func_network.NetworkApi;
import com.xoliu.module_poem.comment.CommentService;

import bean.Poem;
import bean.card_picBean;

/***
 * 诗句页面的Main仓库
 * @return
 * @author xoliu
 * @create 23-11-21
 **/

public class PoemMainRepository {





    /***
     * 请求card的上层图片
     * @return androidx.lifecycle.MutableLiveData<bean.card_picBean>
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
                Log.d("TAG", "卡片背景图片加载成功，url：" + cardPicBean.getImgurl());
            }

            @Override
            public void onFailure(Throwable e) {
                Log.d("getCardPic()", "onFailure: 加载失败！");
            }

        }));

        return cardPic;
    }


    /***
     * 获取用户头像
     *
     * @return androidx.lifecycle.MutableLiveData<bean.card_picBean>
     * @author xoliu
     * @create 23-11-22
     **/
    @SuppressLint("CheckResult")
    public MutableLiveData<card_picBean> getuserIcon(){
        MutableLiveData<card_picBean> userIcon = new MutableLiveData<>();
        CommentService commentService = NetworkApi.createService(CommentService.class);
        commentService.getUserPic().compose(NetworkApi.applySchedulers(new BaseObserver<card_picBean>() {
            @Override
            public void onSuccess(card_picBean cardPicBean) {
                userIcon.postValue(cardPicBean);
                //Log.d("TAG", "获取用户头像url = " + cardPicBean.getImgurl());
            }

            @Override
            public void onFailure(Throwable e) {
                Log.d("getCardPic()", "onFailure: 加载失败！");
            }

        }));

        return userIcon;
    }


    //https://v1.hitokoto.cn/?c=i&encode=json 为诗词的数据接口
    @SuppressLint("CheckResult")
    public MutableLiveData<Poem> getPoem(){
        MutableLiveData<Poem> poemData = new MutableLiveData<>();
        CardPicService service = NetworkApi.createService(CardPicService.class);
        service.getPoem("https://v1.hitokoto.cn/?c=i&c=k&encode=json").compose(NetworkApi.applySchedulers(new BaseObserver<Poem>() {
            @Override
            public void onSuccess(Poem poem) {
                Log.d("TAG", "获取了诗词,正文：" + poem.getHitokoto());
                poemData.postValue(poem);
            }

            @Override
            public void onFailure(Throwable e) {

            }
        }));

        return poemData;
    }
}
