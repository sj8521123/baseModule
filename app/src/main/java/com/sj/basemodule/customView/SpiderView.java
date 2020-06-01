/*
package com.sj.basemodule.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

*/
/**
 * @author shijun
 * <p>
 * Created on 2020/5/20.
 *//*

public class SpiderView extends View {
    private Paint radarPaint,valuePaint;
    private float radius;
    private int centerX;
    private int centerY;
    private int count = 6;
    public SpiderView(Context context) {
        super(context);
        init();
    }

    public SpiderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpiderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        radarPaint = new Paint();
        radarPaint.setStyle(Paint.Style.STROKE);
        radarPaint.setColor(Color.GREEN);

        valuePaint = new Paint();
        valuePaint.setColor(Color.BLUE);
        valuePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制蜘蛛网络
        drawPolygon(canvas);
        //画网格中线
       */
/* drawLines(canvas);
        //画数据图
        drawRegion(canvas);*//*


    }
    //蜘蛛网络
    private void drawPolygon(Canvas canvas){
        Path path = new Path();
        float r = radius / count;
        for(int i = 1; i <= count; i++){
            float curR = i * r;
            path.reset();
            for(int j = 0; j < count; j++){
                if(j == 0){
                    path.moveTo(centerX + curR,centerY);
                }else{
                    float x = ()
                }
            }
        }
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(w,h)/2 * 0.9f;
        centerX = w/2;
        centerY = h/2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
*/
