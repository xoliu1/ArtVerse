package com.xoliu.module_poem.net;

import com.xoliu.module_poem.model.bean.Poemt;

import global.CardPic;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface CardService {

        /**
         * 博天随机图片
         */
        /*

        名称	必填	 类型	                说明
 	    method	否	    string	        输出壁纸端[mobile|pc|zsy]默认为pc
 	    lx	    否	    string	        选择输出分类[meizi|dongman|fengjing|suiji]，为空随机输出
       	format	否	    string	        输出壁纸格式[json|images]默认为images
         */
        @GET("sjtx/api.php?lx=c3&format=json")
        Observable<CardPic> getCardPic();

        /**
         * 获取诗词
         *
         * @author xoliu
         * @create 23-11-22
         **/
        @GET
        Observable<Poemt> getPoem(@Url String url);



}
