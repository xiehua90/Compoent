package com.example.thinkdo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import java.util.Locale;
import java.util.Random;


public class GestureImageView extends ImageView {

    private RemoteGestureDetector detector;

    private ScaleGestureDetector scaleGestureDetector;
    Matrix matrix = new Matrix();

    int[] pixels = new int[1920 * 1080];
    Bitmap bitmap;


    final int width = 1920, height = 1080;

    int x, y;

    public GestureImageView(Context context) {
        super(context);
        super.setScaleType(ScaleType.MATRIX);
        initGestureDetector(context);

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0xff00ff00;
        }

        bitmap = Bitmap.createBitmap(1920, 1080, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
    }

    private void initGestureDetector(Context context) {
        detector = new RemoteGestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                log("onDoubleTap");
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                log("onSingleTapConfirmed");

                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                log("onScroll");
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                log("onFling");
                return true;
            }
        },
                new RemoteGestureDetector.OnTwoPointerGestureListener() {
                    @Override
                    public boolean onTwoPointerScale(RemoteGestureDetector detector) {
                        float factor = detector.getScaleFactor() * getScale();
                        log(String.format(Locale.CHINA,
                                "onTwoScale (focusX=%d, focusY=%d) factor=%.3f, orginal factor=%.3f",
                                detector.getFocusX(), detector.getFocusY(),
                                factor, detector.getScaleFactor()
                        ));
                        matrix.setScale(factor, factor, detector.getFocusX(), detector.getFocusY());
//                        matrix.setTranslate(0, 0);
                        setImageMatrix(matrix);
                        return true;
                    }

                    @Override
                    public boolean onTwoPointerDrag(RemoteGestureDetector detector) {
                        log(String.format(Locale.CHINA,
                                "onTwoPointerDrag (offsetX=%d, offsetY=%d)",
                                detector.getOffsetX(), detector.getOffsetY()));
//                        computeOffset(detector.getOffsetX(), detector.getOffsetY());

//                        matrix.setTranslate(offsetX, offsetY);
//                        matrix.setScale(1, 1);
//                        setImageMatrix(matrix);


                        return true;
                    }

                    @Override
                    public boolean onTwoPointerTap() {
                        log("onTwoPointerTap");
                        return true;
                    }


                });

        detector.getGestureDetector().setIsLongpressEnabled(false);


        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float factor = detector.getScaleFactor() * getScale();
                log(String.format(Locale.CHINA,
                        "onScale (focusX=%.1f, focusY=%.1f) factor=%.3f",
                        detector.getFocusX(), detector.getFocusY(),
                        factor
                ));

                matrix.setScale(factor, factor, detector.getFocusX(), detector.getFocusY());
//                        matrix.setTranslate(0, 0);
                setImageMatrix(matrix);
                return true;
            }
        });
        scaleGestureDetector.setQuickScaleEnabled(false);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    private float getScale() {
        float[] values = new float[9];
        matrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    float offsetX, offsetY;

    private void computeOffset(float dx, float dy) {
        float[] values = new float[9];
        matrix.getValues(values);
        offsetX = dx + values[Matrix.MTRANS_X];
        offsetY = dy + values[Matrix.MTRANS_Y];
    }

    @Override
    public void draw(Canvas canvas) {
        log("draw()>>");
//        super.draw(canvas);

//        canvas.drawBitmap(pixels, 0, 1920, 0, 0, 1920, 1080, false, null);

        canvas.drawBitmap(bitmap, 0, 0, null);


    }

    private void copy(int x, int y) {
        int pixel = random.nextInt() | 0xFF000000;
        for (int dy = y; dy < y + 64; dy++) {
            int offset = getOffset(x, dy);
            for (int dx = 0; dx < 64; dx++) {
                pixels[offset + dx] = pixel;
            }
        }


        bitmap.setPixels(pixels, getOffset(x, y), width, x, y, 64, 64);

    }


    private int getOffset(int x, int y) {
        return x + y * 1920;
    }

    Handler handler = new Handler(msg -> {
        invalidate();
        return true;
    });
    Random random = new Random();


    public void startDraw() {
        new Thread(() -> {
            for (int j = 0; ; j = (j + 64) % 1024) {
                for (int i = 0; i < 1920; i += 64) {
                    copy(i, j);
                    handler.sendEmptyMessage(0);
                    log("post()------");
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();
    }

    private void log(String msg) {
        Log.w("TAG", msg);
    }


}
