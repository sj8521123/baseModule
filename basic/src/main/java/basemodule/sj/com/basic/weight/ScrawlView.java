package basemodule.sj.com.basic.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import basemodule.sj.com.basic.R;
import basemodule.sj.com.basic.base.MyApplication;
import basemodule.sj.com.basic.util.file.STGFileUtil;


/**
 * content: 自定义涂鸦板(双缓冲机制)
 * author：sj
 * time: 2017/6/25 12:37
 * email：13658029734@163.com
 * phone:13658029734
 */

public class ScrawlView extends View {
    private float preX;
    private float preY;
    private Path path;
    private Paint paint;
    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;
    private IShow iShow;
    private boolean isShowReset;

    public ScrawlView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.scrawl);
        float strokeWidth = a.getDimension(R.styleable.scrawl_strokeWidth, 6);
        int strokeColor = a.getColor(R.styleable.scrawl_strokeColor, 0xff000000);

        int screen_width = MyApplication.screenWidth;
        int screen_height = MyApplication.screenHeight;
        //因为是横屏
        cacheBitmap = Bitmap.createBitmap(screen_height, screen_width, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas(cacheBitmap);
        //设置缓存画布的背景色
        cacheCanvas.drawColor(Color.WHITE);
        path = new Path();
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setColor(strokeColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(strokeWidth);
        paint.setAntiAlias(true);
        paint.setDither(true);
        a.recycle();

    }

    public void setiShow(IShow iShow) {
        this.iShow = iShow;
        isShowReset = true;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //显示内存中cacheBitmap
        canvas.drawBitmap(cacheBitmap, 0, 0, null);
        //显示path
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - preX);
                float dy = Math.abs(y - preY);
                //设置滑动的范围，如果x或者y小于5pixel则不绘制
                if (dx >= 5 || dy >= 5) {
                    path.quadTo(preX, preY, x, y);
                    preX = x;
                    preY = y;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isShowReset) {
                    iShow.show();
                    isShowReset = false;
                }
                //绘制到bitmap中
                cacheCanvas.drawPath(path, paint);
                path.reset();
                break;
        }
        //更新view画布
        invalidate();
        return true;
    }

    //清除画板
    public void clear() {
        if (cacheBitmap != null) {
            isShowReset = true;
            /*path.reset();*/
            //清除bitmap保存
            cacheCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            //设置bitmap背景
            cacheCanvas.drawColor(Color.WHITE);
            //更新view画布
            invalidate();
        }
    }

    public String save() throws IOException {
        //保存的文件名
        return saveBitmap(UUID.randomUUID() + ".png");
    }

    private String saveBitmap(String fileName) throws IOException {
        File file = new File(STGFileUtil.getPhotoFullDir(), fileName);
        if (STGFileUtil.fileUtil.isFileExists(file)) {
            STGFileUtil.fileUtil.deleteFile(file);
        }
        STGFileUtil.fileUtil.createOrExistsFile(file);
        FileOutputStream fileOs = new FileOutputStream(file);
        //将内存中的bitmap保存到本地（图片）
        cacheBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOs);
        fileOs.flush();
        fileOs.close();
        return file.getAbsolutePath();
    }

    public interface IShow {
        void show();
    }
}
