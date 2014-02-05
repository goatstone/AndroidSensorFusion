/************************************************************************************
 * Copyright (c) 2014 Jose Collas
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 ************************************************************************************/
package com.goatstone.sensorFusion;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

/**
 * BubbleLevelCompass Widget : Takes input from sensors and displays a bubble level and compass.
 * @author Jose Collas
 *
 * @version 0.7 1/26/14.
 */
public class BubbleLevelCompass extends ViewGroup {

    public Pointer pointer;

    private Paint mainPaint;
    private String message = "";

    public BubbleLevelCompass(Context context) {
        super(context);
        float pointerRadius = 12.0f;
        requestFocus();

        // Set up the paint for the label text
        mainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mainPaint.setTextSize(20);
        mainPaint.setColor(Color.BLUE);
        pointer = new Pointer(getContext());
        pointer.setPointerRadius((int) pointerRadius);
        pointer.setPointerColor(Color.GRAY);

        addView(pointer);
    }

    public BubbleLevelCompass(Context context, AttributeSet attrs) {
        super(context, attrs);
        float pointerRadius = 12.0f;
        requestFocus();
        setPadding(20, 20, 20, 20);
        setBackgroundColor(Color.GRAY);

        mainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mainPaint.setTextSize(20);
        mainPaint.setColor(Color.BLUE);
        pointer = new Pointer(getContext());
        pointer.setPointerRadius((int) pointerRadius);
        addView(pointer);

    }

    /**
     * Pointer : represents a UI element that will point to another UI element in order to select it
     */
    private class Pointer extends View {

        private int pointerRadius = 22;
        private int left = 200;
        private int top = 120;
        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private Paint pointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private int azimuth;
        int viewWidth;
        int viewHeight;
        int distanceMult = 1;

        public void setPointerRadius(int r) {
            pointerRadius = r;
        }

        public void setPointerColor(int color) {
            paint.setColor(color);
            invalidate();
        }

        public void setPointerLeft(int left) {
            this.left = (left * distanceMult);
            invalidate();
        }

        public int getPointerLeft() {
            return left;
        }

        public void setPointerTop(int top) {
            this.top = (top * distanceMult);
            invalidate();
        }

        public void setAzimuth(int azimuth) {
            this.azimuth = azimuth;
        }

        //private Handler mainHandler;
        private Handler handler;

        public Pointer(Context context) {
            super(context);
            paint.setStrokeWidth(6.0f);
            paint.setColor(Color.argb(250, 0, 0, 250));

            pointerPaint.setStrokeWidth(3.0f);
            pointerPaint.setColor(Color.argb(200, 250, 10, 10));
            viewWidth = getWidth();
            viewHeight = getHeight();
            left = viewWidth / 2;
            top = viewHeight / 2;
            setBackgroundColor(Color.argb(200, 250, 200, 200));

//            handler = new Handler();
//            handler.postDelayed(runnable, 100);
        }

        private Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("tag", "run: " + new Date());
                //invalidate();
                handler.postDelayed(this, 1000);
            }
        };

        @Override
        protected void onDraw(Canvas canvas) {
            int viewWidth = getWidth();
            int viewHeight = getHeight();

            canvas.translate((viewWidth / 2), (viewHeight / 2));
            canvas.drawCircle(0 + left, 0 + top - 150, pointerRadius, pointerPaint);
            canvas.rotate(azimuth * -1 - 90);
            canvas.drawLine(0, 0, 50, 0, paint);

        }
    } // end Pointer

    public void setPLeft(int left) {
        pointer.setPointerLeft(left);
    }

    public void setPTop(int top) {
        pointer.setPointerTop(top * -1);
    }

    public void setMessage(String message) {
        this.message = message;
        invalidate();
    }

    public void setAzimuth(int azimuth1) {
        pointer.setAzimuth(azimuth1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // Do nothing. Do not call the superclass method--that would start a layout pass
        // on this view's children. ?? - RoundDial lays out its children in onSizeChanged(). ??
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawText("CustomView : pointer.getPointerLeft() : " + pointer.getPointerLeft(), 10, 300, mainPaint);
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return 400;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return (int) 400;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(400, 500);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        pointer.layout(0, 0, w, h);
    }

}
