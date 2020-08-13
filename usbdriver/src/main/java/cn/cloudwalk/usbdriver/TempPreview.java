package cn.cloudwalk.usbdriver;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * create by 曾强 on 2020/3/25
 */
public class TempPreview extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private SurfaceHolder mHolder; // 用于控制SurfaceView

    private boolean drawTempValue;

    private Thread t; // 声明一条线程

    private boolean flag; // 线程运行的标识，用于控制线程

    private Canvas mCanvas; // 声明一张画布

    private Paint p; // 声明一支画笔

    private Bitmap bitmap;

    private float tempValue;

    private long lastDrawTime;

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setTempValue(float tempValue) {
        this.tempValue = tempValue;
    }

    public TempPreview(Context context) {
        this(context, null);
        lastDrawTime = System.currentTimeMillis();
    }

    private void setTextPaintColor() {
        if (tempValue > 37) {
            p.setColor(Color.parseColor("#ff9940"));
        } else if (tempValue > 38) {
            p.setColor(Color.RED);
        } else {
            p.setColor(Color.GREEN);
        }
    }


    public Bitmap getBitmap() {
        return System.currentTimeMillis() - lastDrawTime < 10 * 1000 ? bitmap : null;
    }

    public float getTempValue() {
        return System.currentTimeMillis() - lastDrawTime < 10 * 1000 ? tempValue : 0;
    }

    public TempPreview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TempPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHolder = getHolder(); // 获得SurfaceHolder对象
        mHolder.addCallback(this); // 为SurfaceView添加状态监听
        p = new Paint(); // 创建一个画笔对象
        p.setDither(true);
        p.setAntiAlias(true);
        p.setFakeBoldText(true);
        p.setTextSize(14);
        setFocusable(true); // 设置焦点
    }

    public boolean isDrawTempValue() {
        return drawTempValue;
    }

    public void setDrawTempValue(boolean drawTempValue) {
        this.drawTempValue = drawTempValue;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        t = new Thread(this); // 创建一个线程对象
        flag = true; // 把线程运行的标识设置成true
        t.start(); // 启动线程
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false; // 把线程运行的标识设置成false
    }

    /**
     * 自定义一个方法，在画布上画一个圆
     */
    public void draw() {
        mCanvas = mHolder.lockCanvas(); // 获得画布对象，开始对画布画画
        if (mCanvas != null) {
            //清屏
            p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            mCanvas.drawPaint(p);
            p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            if (bitmap != null) {
                mCanvas.drawBitmap(bitmap, 0, 0, p);
                String text = String.format("%.1f℃", tempValue);
                Rect bounds = new Rect();
                p.getTextBounds(text, 0, text.length(), bounds);
                int x = (bitmap.getWidth() - bounds.width()) / 2;
                int y = bitmap.getHeight() + bounds.height() + 5;
                p.setColor(Color.WHITE);
                mCanvas.drawRect(0, bitmap.getHeight(), getWidth(), getHeight() + bounds.height() + 5, p);
                setTextPaintColor();
                mCanvas.drawText(text, x, y, p);
            }
            lastDrawTime = System.currentTimeMillis();
            mHolder.unlockCanvasAndPost(mCanvas); // 完成画画，把画布显示在屏幕上
        }
    }

    @Override
    public void run() {
        while (flag) {
            try {
                draw(); // 调用自定义画画方法
                Thread.sleep(50); // 让线程休息50毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
