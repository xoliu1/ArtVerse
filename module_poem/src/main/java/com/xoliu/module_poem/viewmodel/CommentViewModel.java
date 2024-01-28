package com.xoliu.module_poem.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.xoliu.module_poem.model.PoemMainRepository;

import bean.CardPic;

public class CommentViewModel extends ViewModel {
    public MutableLiveData<CardPic> userPic = new MutableLiveData<>();
    public MutableLiveData<CardPic> getUserPic() {

        userPic = new PoemMainRepository().getuserIcon();
        return userPic;
    }



}
