package com.iopixel;

/*
            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                    Version 2, December 2011

 Copyright (C) 2011 <laurent.mallet_at_gmail.com>

 Everyone is permitted to copy and distribute verbatim or modified
 copies of this license document, and changing it is allowed as long
 as the name is changed.

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

  0. You just DO WHAT THE FUCK YOU WANT TO.
*/

/* Support us on http://www.iopixel.com */

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * GLES20Renderer: the OGLES 2.0 Thread.
 */
public class GLES20Renderer implements GLSurfaceView.Renderer {

    public final static int MAX_POINTS = 10;

    private Episode1 mActivity;

    private GLSLProgram mProgramme1;

    private Vertices mVertices;

    private Timer mTimer;



    GLES20Renderer(Episode1 activity) {
        mActivity = activity;
        mTimer = new Timer();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        // create a GLSLProgram
        mProgramme1 = new GLSLProgram(mActivity, "pointsprite");
        // build the program
        mProgramme1.make();

        // Vertices is our 3D object. It contains vertices and indices
        mVertices = new Vertices();

        for (int i=0; i < MAX_POINTS; i++) {
            // create a verte
            P3FT2FR4FVertex v = new P3FT2FR4FVertex();
            v.setPos(getRamdom()*80.f, getRamdom()*80.f, 0);
            v.setColor(getRamdom(), getRamdom(), getRamdom(), 1.f);
            mVertices.putVertice(i, v);
            mVertices.putIndice(i, i);
        }

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mTimer.addMark();
        mTimer.logFPS();
        mActivity.mHandler.post(new Runnable() {
            @Override
            public void run() {
                mActivity.setTitle("" + mTimer.getFPS() + "FPS");
            }
        });

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        mProgramme1.use();
        mProgramme1.draw(mVertices, GLES20.GL_POINTS, MAX_POINTS);
    }

    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e("EllisMarkov", op + ": glError " + error);
        }
    }

    private float getRamdom() {
        float value = (float) (Math.random() * 2. - 1.);
        return value;
    }
}
