package basemodule.sj.com.basic.weight.transform;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * viewPager中的item添加显示动画
 * 0.1的缩放   0.5的透明度
 */
public class AlphaAndScalePageTransformer implements ViewPager.PageTransformer {
    private float scale_max = 0.9F;
    private float alpha_max = 0.5F;

    public AlphaAndScalePageTransformer() {
    }

    public AlphaAndScalePageTransformer(float scaleMax, float alphaMax) {
        this.scale_max = scaleMax;
        this.alpha_max = alphaMax;
    }

    public void transformPage(View page, float position) {
        float scale = (position < 0)
                ? ((1 - scale_max) * position + 1)
                : ((scale_max - 1) * position + 1);
        float alpha = (position < 0)
                ? ((1 - alpha_max) * position + 1)
                : ((alpha_max - 1) * position + 1);
        if (position < 0) {
            page.setPivotX(page.getWidth());
            page.setPivotY(page.getHeight() / 2);
        } else {
            page.setPivotX(0);
            page.setPivotY(page.getHeight() / 2);
        }
        page.setScaleX(scale);
        page.setScaleY(scale);
        page.setAlpha(Math.abs(alpha));
    }
}
