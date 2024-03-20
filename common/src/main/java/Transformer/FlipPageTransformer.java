package Transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

/***
 * 页面转换器【类似翻页效果-未成功】
 * 用于ViewPager2的切换
 * @author xoliu
 * @create 23-12-5
 **/
public class FlipPageTransformer implements ViewPager2.PageTransformer {
    private static final float MIN_SCALE = 0.75f;
    private static final float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        if (position < -1) { // 页面在左边屏幕外
            page.setAlpha(0f);
        } else if (position <= 0) { // 页面在中心位置或者往左滑动
            page.setAlpha(1f);
            page.setTranslationX(0f);
            page.setScaleX(1f);
            page.setScaleY(1f);
        } else if (position <= 1) { // 页面在中心位置或者往右滑动
            page.setAlpha(1f - position);
            page.setTranslationX(pageWidth * -position);
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else { // 页面在右边屏幕外
            page.setAlpha(0f);
        }
    }

}
