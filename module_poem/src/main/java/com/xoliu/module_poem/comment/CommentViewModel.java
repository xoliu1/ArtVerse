package com.xoliu.module_poem.comment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.xoliu.module_poem.viewpager.PoemMainRepository;

import bean.CardPic;

public class CommentViewModel extends ViewModel {
    public MutableLiveData<CardPic> userPic = new MutableLiveData<>();
    public MutableLiveData<CardPic> getUserPic() {

        userPic = new PoemMainRepository().getuserIcon();
        return userPic;
    }



}
