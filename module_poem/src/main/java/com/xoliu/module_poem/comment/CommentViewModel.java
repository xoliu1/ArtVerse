package com.xoliu.module_poem.comment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.xoliu.module_poem.viewpager.PoemMainRepository;
import bean.card_picBean;

public class CommentViewModel extends ViewModel {
    public MutableLiveData< card_picBean> userPic = new MutableLiveData<>();
    public MutableLiveData<card_picBean> getUserPic() {

        userPic = new PoemMainRepository().getuserIcon();
        return userPic;
    }



}
