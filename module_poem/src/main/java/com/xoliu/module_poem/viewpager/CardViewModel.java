package com.xoliu.module_poem.viewpager;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import bean.Poem;
import bean.card_picBean;

/***
 * 作为card的ViewModel，主要用于加载图片框架
 * @author xoliu
 * @create 23-11-21
 **/

public class CardViewModel extends ViewModel {

    public MutableLiveData<Poem> poem = null;
    public MutableLiveData<card_picBean> cardPic;
    PoemMainRepository repository = new PoemMainRepository();

    public MutableLiveData<card_picBean> getCardPic() {
        if (cardPic == null){
            cardPic = repository.getCardPic();
            Log.d("TAG", "View Model 获取卡片背景");
        }
        return cardPic;
    }

    public MutableLiveData<Poem> getPoem() {
        if(poem == null){
            poem = repository.getPoem();
            Log.d("TAG", "View Model 获取诗词正文");
        }
        return poem;
    }
}
