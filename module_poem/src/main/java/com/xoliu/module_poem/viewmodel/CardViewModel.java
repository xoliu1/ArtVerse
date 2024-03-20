package com.xoliu.module_poem.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.xoliu.module_poem.model.PoemMainRepository;
import com.xoliu.module_poem.model.bean.Poemt;

import global.CardPic;

/***
 * 作为card的ViewModel，主要用于加载图片框架
 * @author xoliu
 * @create 23-11-21
 **/

public class CardViewModel extends ViewModel {

    public MutableLiveData<Poemt> poem = null;
    public MutableLiveData<CardPic> cardPic;
    PoemMainRepository repository = new PoemMainRepository();

    /***
     * 因重构已弃用
     *
     * @return androidx.lifecycle.MutableLiveData<global.CardPic>
     * @author xoliu
     * @create 24-1-28
     **/
    public MutableLiveData<CardPic> getCardPic() {
        if (cardPic == null){
            //cardPic = repository.getCardPic();
           // Log.d("TAG", "View Model 获取卡片背景");
        }
        return cardPic;
    }

    public MutableLiveData<Poemt> getPoem() {
        if(poem == null){
            poem = repository.getPoem();
            Log.d("TAG", "View Model 获取诗词正文1" );
        }
        return poem;
    }
}
