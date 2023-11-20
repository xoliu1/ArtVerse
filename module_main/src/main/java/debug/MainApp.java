package debug;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
    }
}
