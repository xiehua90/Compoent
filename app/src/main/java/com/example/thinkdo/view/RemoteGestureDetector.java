package com.example.thinkdo.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.thinkdo.compoentdemo.R;

public class RemoteGestureDetector {
    private GestureDetector gestureDetector;
    private OnTwoPointerGestureListener twoPointerGestureListener;

    private boolean startSimpleGestureTouch = true;

    private static final int TWO_GESTURE_END = 1;

    private static final int DELAY_TWO_GESTURE_END = 150;

    private RemoteGestureHandler mHandler;

    public RemoteGestureDetector(Context context,
                                 GestureDetector.SimpleOnGestureListener gestureListener,
                                 OnTwoPointerGestureListener twoPointerGestureListener) {
        float touchSlop = context.getResources().getDimensionPixelOffset(R.dimen.touch_slop);
        slopSquare = touchSlop * touchSlop;

        mHandler = new RemoteGestureHandler();
        if (gestureListener != null)
            gestureDetector = new GestureDetector(context, gestureListener);
        if (twoPointerGestureListener != null)
            this.twoPointerGestureListener = twoPointerGestureListener;
    }

    private class RemoteGestureHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case TWO_GESTURE_END:
                    startSimpleGestureTouch = true;
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    public GestureDetector getGestureDetector() {
        return gestureDetector;
    }


    public interface OnTwoPointerGestureListener {

        public boolean onTwoPointerScale(RemoteGestureDetector detector);

        public boolean onTwoPointerDrag(RemoteGestureDetector detector);

        public boolean onTwoPointerTap();
    }

    public boolean onTouchEvent(MotionEvent event) {
        curAction = event.getAction() & MotionEvent.ACTION_MASK;
        int pointerCount = event.getPointerCount();

        if (curAction == MotionEvent.ACTION_DOWN) {
            startSimpleGestureTouch = true;
            removeTwoGestureOverMessage();
        }

        if (pointerCount == 2) {
            return onTwoPointerTouchEvent(event);
        }

        cancelDoublePointer();

        if (startSimpleGestureTouch && gestureDetector != null) gestureDetector.onTouchEvent(event);
        return true;
    }

    private float x1, y1, x2, y2;
    private float lastX1, lastY1, lastX2, lastY2;
    private float lastDownX1, lastDownY1, lastDownX2, lastDownY2;

    private boolean mAlwaysInTapRegion = true;
    private boolean twoPointerInProgress = false;

    private int curAction;
    private float slopSquare;

    private boolean scaleInProgress = false;
    private boolean dragInProgress = false;
    private boolean transFlag = true;
    private int minSpan = 10;


    private boolean onTwoPointerTouchEvent(MotionEvent event) {
        x1 = event.getX(0);
        y1 = event.getY(0);
        x2 = event.getX(1);
        y2 = event.getY(1);

        if (twoPointerInProgress) {
            if (mAlwaysInTapRegion) {
                //tap
                float deltaX1 = x1 - lastDownX1;
                float deltaY1 = y1 - lastDownY1;
                float deltaX2 = x2 - lastDownX2;
                float deltaY2 = y2 - lastDownY2;

                float distance1 = deltaX1 * deltaX1 + deltaY1 * deltaY1;
                float distance2 = deltaX2 * deltaX2 + deltaY2 * deltaY2;

                switch (curAction) {
                    case MotionEvent.ACTION_POINTER_UP:
                        startSimpleGestureTouch = false;
                        if (twoPointerGestureListener != null)
                            twoPointerGestureListener.onTwoPointerTap(); //注意这里没有break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        mHandler.sendEmptyMessageDelayed(TWO_GESTURE_END, DELAY_TWO_GESTURE_END);
                        break;
                    default:
                        if (distance1 > slopSquare || distance2 > slopSquare) {
                            mAlwaysInTapRegion = false;
                            doublePointerScrollHandler(event);
                        }
                }
            } else {
                //scroll
                doublePointerScrollHandler(event);
                if (curAction == MotionEvent.ACTION_POINTER_DOWN ||
                        curAction == MotionEvent.ACTION_POINTER_UP) {
                    mHandler.sendEmptyMessageDelayed(TWO_GESTURE_END, DELAY_TWO_GESTURE_END);
                }
            }
        } else {
            twoPointerInProgress = true;
            setDoubleLastDownPointer();
        }
        setDoubleLastPointer();

        return true;
    }

    public int getFocusX() {
        return (int) ((x1 + x2) / 2);
    }

    public int getFocusY() {
        return (int) ((y1 + y2) / 2);
    }

    public int getOffsetX1() {
        return (int) (x1 - lastX1);
    }

    public int getOffsetX2() {
        return (int) (x2 - lastX2);
    }

    public int getOffsetY1() {
        return (int) (y1 - lastY1);
    }

    public int getOffsetY2() {
        return (int) (y2 - lastY2);
    }

    public int getOffsetX() {
        return (getOffsetX1() + getOffsetX2()) / 2;
    }

    public int getOffsetY() {
        return (getOffsetY1() + getOffsetY2()) / 2;
    }


    public float getScaleFactor() {
        float curSpan = (float) Math.hypot((x1 - x2), (y1 - y2));
        float preSpan = (float) Math.hypot((lastX1 - lastX2), (lastY1 - lastY2));

        return preSpan > 0 ? curSpan / preSpan : 1;
    }

    private void doublePointerScrollHandler(MotionEvent event) {
        float dx1, dy1, dx2, dy2;
        if (!transFlag) {
            dx1 = x1 - lastX1;
            dy1 = y1 - lastY1;
            dx2 = x2 - lastX2;
            dy2 = y2 - lastY2;
        } else {
            dx1 = x1 - lastDownX1;
            dy1 = y1 - lastDownY1;
            dx2 = x2 - lastDownX2;
            dy2 = y2 - lastDownY2;
        }
        int span1 = (int) Math.hypot(dx1, dy1);
        int span2 = (int) Math.hypot(dx2, dy2);

//        log(String.format(Locale.CHINESE, "(dx1=%.1f, dy1=%.1f) (dx2=%.1f, dy2=%.1f)", dx1, dy1, dx2, dy2));

        int maxSpan = span1 > span2 ? span1 : span2;

        if (!(span1 == 0 && span2 == 0)) {
            if (span1 == 0 || span2 == 0) {
                if (span1 == 0) {
                    dx1 = x1 - x2;
                    dy1 = y1 - y2;
                } else {
                    dx2 = x2 - x1;
                    dy2 = y2 - x1;
                }

                boolean below45 = isBelow45(dx1, dy1, dx2, dy2);
                if (below45) {
                    scaleIntent(event, maxSpan);
                } else {
                    dragIntent(event, maxSpan);
                }
            } else {
                boolean opposeDir = dx1 * dx2 + dy1 * dy2 < 0;

                if (opposeDir) {
                    scaleIntent(event, maxSpan);
                } else if (isBelow45(dx1, dy1, dx2, dy2)) {
                    dragIntent(event, maxSpan);

                }
            }
        }
    }

    private void scaleIntent(MotionEvent event, int maxSpan) {
        if (scaleInProgress) {
            if (twoPointerGestureListener != null)
                twoPointerGestureListener.onTwoPointerScale(this);
        } else if (maxSpan > minSpan) {
            scaleInProgress = true;
            transFlag = false;
            dragInProgress = false;
            if (twoPointerGestureListener != null)
                twoPointerGestureListener.onTwoPointerScale(this);

            removeTwoGestureOverMessage();
            startSimpleGestureTouch = false;
        } else {
            transFlag = true;
            setDoubleLastDownPointer();
        }
    }

    private void dragIntent(MotionEvent event, int maxSpan) {
        if (dragInProgress) {
            if (twoPointerGestureListener != null) twoPointerGestureListener.onTwoPointerDrag(this);
        } else if (maxSpan > 2 * minSpan) {
            dragInProgress = true;
            transFlag = false;
            scaleInProgress = false;
            removeTwoGestureOverMessage();
            startSimpleGestureTouch = false;
            if (twoPointerGestureListener != null) twoPointerGestureListener.onTwoPointerDrag(this);
        } else {
            transFlag = true;
            setDoubleLastDownPointer();
        }
    }


    private void removeTwoGestureOverMessage() {
        if (mHandler.hasMessages(TWO_GESTURE_END)) {
            mHandler.removeMessages(TWO_GESTURE_END);
        }
    }

    private void cancelDoublePointer() {
        mAlwaysInTapRegion = true;
        twoPointerInProgress = false;
        dragInProgress = false;
        scaleInProgress = false;
        transFlag = true;
    }

    private void setDoubleLastDownPointer() {
        lastDownX1 = x1;
        lastDownY1 = y1;
        lastDownX2 = x2;
        lastDownY2 = y2;
    }

    private void setDoubleLastPointer() {
        lastX1 = x1;
        lastY1 = y1;
        lastX2 = x2;
        lastY2 = y2;
    }

    private boolean isBelow45(float x1, float y1, float x2, float y2) {
        double len1 = Math.hypot(x1, y1);
        double len2 = Math.hypot(x2, y2);

        float p = Math.abs(x1 * x2 + y1 * y2);

        return Math.sqrt(2) * p > len1 * len2;
    }

    private void log(String msg) {
        Log.w("TAG", msg);
    }


}
