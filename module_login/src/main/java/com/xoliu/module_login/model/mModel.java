package com.xoliu.module_login.model;

import android.os.Handler;

public interface mModel {
    public void login(String x, String y, Handler handler);
    public void reg(String x,String y,String z,String k,Handler handler);
    public void logat(String cm,Handler handler);
}
