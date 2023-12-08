package Transformer;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;
/***
 * 页面转换器【淡入淡出 】
 * 用于ViewPager2的切换
 * @author xoliu
 * @create 23-12-5
 **/
public class FadeInOutPageTransformer implements ViewPager2.PageTransformer {
    private static final float MIN_SCALE = 0.75f;
    private static final float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1) { // 页面已经滑出屏幕左侧
            page.setAlpha(0f);
        } else if (position <= 1) { // 页面在屏幕范围内
            // 设置透明度和缩放比例
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            // 设置透明度
            page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        } else { // 页面已经滑出屏幕右侧
            page.setAlpha(0f);
        }
    }
}


