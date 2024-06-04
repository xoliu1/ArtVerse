package com.xoliu.module_music.model;

import com.xoliu.module_music.R;
import com.xoliu.module_music.model.bean.Recitation;
import com.xoliu.module_music.model.bean.Song;

import java.util.ArrayList;
import java.util.List;

public class MusicRepository {
    private static MusicRepository instance;
    private List<Song> songs = new ArrayList<>();;
    private List<Recitation> recitations= new ArrayList<>();;

    private MusicRepository() {
        songs.add(new Song("氓","佚名 〔先秦〕"));
        songs.add(new Song("月夜忆舍弟","杜甫 〔唐代〕"));
        songs.add(new Song("江南","汉乐府 〔两汉〕"));
        songs.add(new Song("如意娘","武则天 〔唐代〕"));
        songs.add(new Song("鸿雁","诗经(先秦)"));
        songs.add(new Song("骊山有感", "唐-李商隐"));
        recitations.add(new Recitation("朗诵-蟾宫曲·春情", R.drawable.bg_1));
        recitations.add(new Recitation("朗诵-浣溪沙·谁念西风独自凉", R.drawable.bg_2));
        recitations.add(new Recitation("作画-虞美人·琵琶-宋-苏轼", R.drawable.bg_3));
        recitations.add(new Recitation("唱曲-古宴曲-唐-于濆", R.drawable.bg_4));
        recitations.add(new Recitation("朗诵-思母-元-与恭", R.drawable.bg_5));
        recitations.add(new Recitation("吟诗-过华清宫绝句三首-唐-杜牧", R.drawable.bg_6));
    }

    public static MusicRepository getInstance() {
        if (instance == null) {
            synchronized (MusicRepository.class) {
                if (instance == null) {
                    instance = new MusicRepository();
                }
            }
        }
        return instance;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public List<Recitation> getRecitations() {
        return recitations;
    }
}

