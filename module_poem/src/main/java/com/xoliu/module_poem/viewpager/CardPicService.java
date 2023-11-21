package com.xoliu.module_poem.viewpager;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CardPicService {

        /**
         * 博天随机图片
         */
        /*

        名称	必填	 类型	                说明
 	    method	否	    string	        输出壁纸端[mobile|pc|zsy]默认为pc
 	    lx	    否	    string	        选择输出分类[meizi|dongman|fengjing|suiji]，为空随机输出
       	format	否	    string	        输出壁纸格式[json|images]默认为images
         */
        @GET("sjbz/api.php?lx=dongman&format=json")
        Observable<card_picBean> getCardPic();

}
