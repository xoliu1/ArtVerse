package com.xoliu.module_poem.net;

import global.CardPic;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CommentService {
    /**
     * 博天随机头像
     */
    /*
    名称	必填	类型	                说明
 	method	否	    string	    输出壁纸端[mobile(手机端),pc（电脑端）,zsy（手机电脑自动判断）]默认为pc
 	lx	    否	    string	    输出头像类型[a1（男头）|b1（女头）|c1（动漫头像）|c2（动漫女头）|c3（动漫男头）]默认为c1
 	format	否	    string	    输出壁纸格式[json|images]默认为images
     */
    @GET("sjtx/api.php?lx=a1&format=json")
    Observable<CardPic> getUserPic();
    //返回json和壁纸的格式一样，直接用


}
