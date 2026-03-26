package com.xoliu.module_profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xoliu.module_profile.databinding.FragmentProfileArtLikesBinding;

import java.util.ArrayList;
import java.util.List;

import db.bean.ArtContent;
import db.AppDatabase;
import db.bean.PoemCard;

public class ProfileArtLikesFragment extends Fragment {
    private FragmentProfileArtLikesBinding binding;

    List<ArtContent> artContents;
    private Handler handler = new Handler(Looper.getMainLooper());

    public ProfileArtLikesFragment() {
        // Required empty public constructor
    }

    public static ProfileArtLikesFragment newInstance() {
        ProfileArtLikesFragment fragment = new ProfileArtLikesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding  = FragmentProfileArtLikesBinding.inflate(inflater);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();

    }

    private void initView() {
        binding.profileArtRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.profileArtRecyclerView.setAdapter(new ArtContentAdapter(getContext(),artContents));

    }

    private void initData() {
        artContents = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "PoemCards").build();
                List<ArtContent> allArtContents = db.artContentDao().getAllArtContents();

                // 如果数据库为空，插入假数据
                if (allArtContents == null || allArtContents.isEmpty()) {
                    ArtContent a1 = new ArtContent();
                    a1.setArtContentImg(com.xoliu.module_art.R.drawable.soap_bubbles);
                    a1.setName("Soap Bubbles/肥皂泡");
                    a1.setCreator("Jean Siméon Chardin (法国, 巴黎 1699–1779)");
                    a1.setYear("ca. 1733–34");
                    a1.setMaterial("布面油画");
                    a1.setSize("24 x 24 7/8 in. (61 x 63.2 cm)");
                    a1.setContent("一个年轻人从一杯肥皂水中吹出一个气泡，其彩虹般半透明的表面被阳光照射。一个孩子急切地凝视着窗台，没有打破沉默的专注气氛。");

                    ArtContent a2 = new ArtContent();
                    a2.setArtContentImg(com.xoliu.module_art.R.drawable.view_of_saint_mammes);
                    a2.setName("View of Saint-Mammes/圣马梅斯景观");
                    a2.setCreator("Alfred Sisley (英国 1839-1899)");
                    a2.setYear("c.1880");
                    a2.setMaterial("布面油画");
                    a2.setSize("54.61x73.98 cm");
                    a2.setContent("这幅画取景于圣马梅斯，一座处于塞纳河及卢万河交汇处的港口城市。画面上半部分是广阔的天空，下半部分是波光粼粼的河面，河面上停泊着小船，河岸上排列着房屋。");

                    ArtContent a3 = new ArtContent();
                    a3.setArtContentImg(com.xoliu.module_art.R.drawable.vasewithflowers);
                    a3.setName("A Vase with Flowers/花与花瓶");
                    a3.setCreator("Jacob Vosmaer (荷兰 ca. 1584–1641)");
                    a3.setYear("probably 1613");
                    a3.setMaterial("木板油画");
                    a3.setSize("33 1/2 x 24 5/8 in. (85.1 x 62.5 cm)");
                    a3.setContent("在荷兰的这幅早期花卉画中，一束稀有的花朵被安排在一个陶罐中，陶罐被设置在一个大的石头壁龛中。");

                    db.artContentDao().insert(a1);
                    db.artContentDao().insert(a2);
                    db.artContentDao().insert(a3);
                    allArtContents = db.artContentDao().getAllArtContents();
                }

                for (ArtContent artContent : allArtContents) {
                    artContents.add(artContent);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        initView();
                    }
                });
            }
        }).start();
    }
}