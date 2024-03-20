package com.xoliu.module_music.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.xoliu.module_music.model.MusicRepository;
import com.xoliu.module_music.model.bean.Recitation;
import com.xoliu.module_music.model.bean.Song;

import java.util.List;

public class MusicViewModel extends ViewModel {

    private MusicRepository repository = MusicRepository.getInstance();

    private MutableLiveData<List<Song>> songList;
    private MutableLiveData<List<Recitation>> recitationList;

    public MutableLiveData<List<Song>> getSongList() {
        //songList = repository.getSongList();
        return songList;
    }

    public MutableLiveData<List<Recitation>> getRecitationList() {
        //recitationList = repository.getRecitationList();
        return recitationList;
    }
}
