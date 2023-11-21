package com.xoliu.module_poem.viewpager;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/***
 * 作为card的ViewModel，主要用于加载图片框架
 * @author xoliu
 * @create 23-11-21
 **/

public class CardViewModel extends ViewModel {

    public MutableLiveData<Poem> poem;
    public MutableLiveData<card_picBean> cardPic;

    public MutableLiveData<card_picBean> getCardPic() {
        if (cardPic == null){
            cardPic = new MainRepository().getCardPic();
        }
        return cardPic;
    }

    public MutableLiveData<Poem> getPoem() {
        if(poem == null){
            poem = new MainRepository().getPoem();
        }
        return poem;
    }
}
