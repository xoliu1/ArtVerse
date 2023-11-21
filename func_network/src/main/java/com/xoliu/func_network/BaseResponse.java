package com.xoliu.func_network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/***
 * 返回类，附带返回状态码和错误信息
 * 在通过网络请求返回数据时，先进行一个数据解析，得到结果码和错误信息
 * @author xoliu
 * @create 23-11-21
 **/

public class BaseResponse {

    //返回码
    @SerializedName("res_code")
    @Expose
    public Integer responseCode;

    //返回的错误信息
    @SerializedName("res_error")
    @Expose
    public String responseError;
}
