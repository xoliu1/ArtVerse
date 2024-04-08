package com.xoliu.module_community.Present;

import android.os.Handler;
import android.util.Log;

import com.xoliu.module_community.mModel.lModel;

public class parenteral {

    public lModel model ;


    public parenteral() {
        model = new lModel();
    }
    public void gethodel(Handler handler){
        model.getPoem(handler);
        Log.d("TAD", "gethodel: jsd");
    }

}
