package com.sj.basemodule.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author shijun
 * <p>
 * Created on 2020/4/27.
 */
public class BasisView extends View {
    public BasisView(Context context) {
        super(context);
    }

    public BasisView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BasisView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        Region region = new Region(10,10,200,100);
        region.union(new Rect(10,10,50,300));
        drawRegion(canvas,region,paint);
       /* Path overPath = new Path();
        RectF rectF = new RectF(50,50,200,300);
        overPath.addOval(rectF, Path.Direction.CCW);

        Region region = new Region();
        region.setPath(overPath,new Region(50,50,200,300));
        drawRegion(canvas,region,paint);*/
    /*    Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(50);
        canvas.drawRect(10,100,100,200,paint);

        canvas.drawRegion();

        paint.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(185F,75F,325F,225F);
        canvas.drawRect(rectF,paint);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Rect rect = new Rect(410,100,500,200);
        canvas.drawRect(rect,paint);*/

    }
    private void drawRegion(Canvas canvas ,Region region,Paint paint){
        RegionIterator iterator = new RegionIterator(region);
        Rect rect = new Rect();
        while(iterator.next(rect)){
            canvas.drawRect(rect,paint);
        }
    }
}
