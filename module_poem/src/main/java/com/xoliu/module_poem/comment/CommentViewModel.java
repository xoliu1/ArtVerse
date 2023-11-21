package com.xoliu.module_poem.comment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.xoliu.module_poem.viewpager.MainRepository;
import com.xoliu.module_poem.viewpager.card_picBean;

public class CommentViewModel extends ViewModel {
    public MutableLiveData< card_picBean> userPic = new MutableLiveData<>();
    public MutableLiveData<card_picBean> getUserPic() {

        userPic = new MainRepository().getuserIcon();
        return userPic;
    }



}
