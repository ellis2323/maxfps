package com.ellismarkov;

import android.util.Log;

/** Compute FPS (Frame Per Second) */
public class Timer {

    public static final int VALUES_QTY = 100;

    private long mValues[];

    private int mIndex;

    private long mLastTM;

    public Timer() {
        mLastTM = 0;
        mIndex = 0;
        mValues = new long[VALUES_QTY];
        for (int i=0; i < mValues.length; i++) {
            mValues[i] = 0;
        }
    }

    public void addMark() {
        long now = System.currentTimeMillis();
        long elapsed = now - mLastTM;
        mIndex = mIndex % VALUES_QTY;
        mValues[mIndex] = elapsed;
        mLastTM = now;
        mIndex ++;
    }

    public int getFPS() {
        long sum = 0;
        for (int i=0; i < mValues.length; i++) {
            sum += mValues[i];
        }
        long avg = sum / mValues.length + 1;
        return (int)((long)1000 / avg);
    }

    // variable for logFPS
    private long mLastLogFPS = 0;

    public void logFPS() {
        long now = System.currentTimeMillis();
        long elapsed = now - mLastLogFPS;
        if (elapsed > 1000) {
            mLastLogFPS = now;
            Log.i("EllisMarkov", "FPS: " + getFPS());
        }        
    }

}
