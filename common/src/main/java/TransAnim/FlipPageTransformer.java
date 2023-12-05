package TransAnim;

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
        if (position < -1) { // 页面已经滑出屏幕左侧
            page.setRotationY(0f);
            page.setAlpha(0f);
        } else if (position <= 0) { // 页面在屏幕范围内或者正在滑动进入屏幕
            // 设置翻转角度
            float rotationY = 180f * Math.abs(position);
            page.setRotationY(rotationY);
            // 设置透明度
            page.setAlpha(Math.max(MIN_ALPHA, 1 - Math.abs(position)));
            // 设置缩放比例
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else { // 页面已经滑出屏幕右侧
            page.setRotationY(0f);
            page.setAlpha(0f);
        }
    }
}
