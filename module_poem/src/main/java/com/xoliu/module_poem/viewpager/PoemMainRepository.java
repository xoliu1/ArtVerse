package com.xoliu.module_poem.viewpager;


import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.xoliu.func_network.BaseObserver;
import com.xoliu.func_network.NetworkApi;
import com.xoliu.module_poem.comment.CommentService;

import java.util.concurrent.TimeUnit;

import bean.CardPic;
import bean.Poem;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/***
 * 诗句页面的Main仓库
 * @return
 * @author xoliu
 * @create 23-11-21
 **/

public class PoemMainRepository {





    /***
     * 请求card的上层图片
     * @return androidx.lifecycle.MutableLiveData<bean.CardPic>
     * @author xoliu
     * @create 23-11-21
     **/

    @SuppressLint("CheckResult")
    public MutableLiveData<CardPic> getCardPic() {
        MutableLiveData<CardPic> cardPic = new MutableLiveData<>();
        CardPicService cardPicService = NetworkApi.createService(CardPicService.class);
        cardPicService.getCardPic().compose(NetworkApi.applySchedulers(new BaseObserver<CardPic>() {
            @Override
            public void onSuccess(CardPic cardPicBean) {
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
     * @return androidx.lifecycle.MutableLiveData<bean.CardPic>
     * @author xoliu
     * @create 23-11-22
     **/
    @SuppressLint("CheckResult")
    public MutableLiveData<CardPic> getuserIcon(){
        MutableLiveData<CardPic> userIcon = new MutableLiveData<>();
        CommentService commentService = NetworkApi.createService(CommentService.class);
        commentService.getUserPic().compose(NetworkApi.applySchedulers(new BaseObserver<CardPic>() {
            @Override
            public void onSuccess(CardPic cardPicBean) {
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
        service.getPoem("https://v1.hitokoto.cn/?c=i&encode=json").compose(NetworkApi.applySchedulers(new BaseObserver<Poem>() {
            @Override
            public void onSuccess(Poem poem) {
                Log.d("TAG", "获取了诗词,正文：" + poem.getHitokoto());
                poemData.postValue(poem);
            }



            @Override
            public void onFailure(Throwable e) {
                Log.e("TAG", "获取诗词正文失败！");
            }
        }));

//            @Override
//            public void onFailure(Throwable e) {
//                Log.e("TAG", "获取诗词正文失败！");
//
//                // 发起下一次请求，最多重试3次
//                Observable.just(true)
//                        .delay(1000, TimeUnit.MILLISECONDS)
//                        .flatMap((Function<Boolean, ObservableSource<Poem>>) aBoolean ->
//                                service.getPoem("https://v1.hitokoto.cn/?c=i&c=k&encode=json")
//                        )
//                        .compose(NetworkApi.applySchedulers(new BaseObserver<Poem>() {
//                            @Override
//                            public void onSuccess(Poem poem) {
//                                Log.d("TAG", "获取了诗词,正文：" + poem.getHitokoto());
//                                poemData.postValue(poem);
//                            }
//
//                            @Override
//                            public void onFailure(Throwable e) {
//                                Log.e("TAG", "再次获取诗词正文失败！");
//                            }
//                        }))
//                        .retry(3) // 最多重试3次
//                        .subscribe();
//            }
//        }));

        return poemData;
    }
}
