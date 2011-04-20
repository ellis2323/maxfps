package com.ellismarkov;


import android.app.Activity;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.FloatBuffer;

public class GLSLProgram {

    //! activity
    private Activity mActivity;

    //! GLES20 Program
    private int mProgramObject;
    //! Vertex shader
    private int mVertexShader;
    // Fragment shader
    private int mFragmentShader;

    //! ProgramName (Vertex filename is ProgramName.vsh and Fragment filename is ProgramName.fsh)
    private String mProgramName;

    //! Matrix Model View Projection
    private float[] mMvp = new float[16];

    //! Rotation
    private float[] mRotation = new float[16];
    //! Projection
    private float[] mProjection = new float[16];

    // attibutes
    private int mPositionLoc;
    private int mColorLoc;
    private int mTexCoordLoc;
    // uniform
    private int mMvpLoc;
    private int mScreenSizeLoc;
    private int mTimeLoc;
    private int mMousePos;
    // sampler
    private int mTex0Loc;
    private int mTex1Loc;
    private int mTex2Loc;
    private int mTex3Loc;


    public GLSLProgram(Activity activity, String programName) {
        mActivity = activity;
        mProgramObject = 0;
        mVertexShader = 0;
        mFragmentShader = 0;
        mProgramName = programName;

        // create Program
        mProgramObject = GLES20.glCreateProgram();

        // set MVP to Identity
        //float mProjMatrix[] = new float[16];
        Matrix.orthoM (mProjection, 0, -100, 100, -100, 100, -10.f, 100000.f);
        //Matrix.setIdentityM(mMvp, 0);


    }

    public void delete() {
        // delete Vertex shader
        if (mVertexShader!=0) {
            GLES20.glDeleteShader(mVertexShader);
        }
        // delete Fragment shader
        if (mFragmentShader!=0) {
            GLES20.glDeleteShader(mFragmentShader);
        }
        // delete a GLES20 program
        if (mProgramObject!=0) {
            GLES20.glDeleteProgram(mProgramObject);
        }
    }

    public boolean make() {
        String vShaderFilename = mProgramName + ".vsh";
        String fShaderFilename = mProgramName + ".fsh";
        // load and compile Shaders
        if (loadShaders(vShaderFilename, fShaderFilename)==false) {
            Log.e("EllisMarkov", "Cannot load shaders");
            return false;
        }
        // link
        link();

        // attributes
        mPositionLoc = GLES20.glGetAttribLocation(mProgramObject, "aPosition");
        mColorLoc = GLES20.glGetAttribLocation(mProgramObject, "aColor");
        mTexCoordLoc = GLES20.glGetAttribLocation(mProgramObject, "aTexCoord");
        // uniforms
        mMvpLoc = GLES20.glGetUniformLocation(mProgramObject, "uMvp");
        mScreenSizeLoc = GLES20.glGetUniformLocation(mProgramObject, "uScreenSize");
        mTimeLoc = GLES20.glGetUniformLocation(mProgramObject, "uTime");
        // samplers
        mTex0Loc = GLES20.glGetUniformLocation(mProgramObject, "tex0");
        mTex1Loc = GLES20.glGetUniformLocation(mProgramObject, "tex1");
        mTex2Loc = GLES20.glGetUniformLocation(mProgramObject, "tex2");
        mTex3Loc = GLES20.glGetUniformLocation(mProgramObject, "tex3");

        Log.i("EllisMarkov", "program compiled & linked: " + mProgramName);
        return true;
    }

    static float counter = 0;
    void use() {
        // use program
        GLES20.glUseProgram(mProgramObject);

        if (mMvpLoc != -1) {
            //Log.d("EllisMarkov","setMvp");
            counter += 1.f;
            Matrix.setRotateEulerM(mRotation,0, 0.f, 0.f, counter);
            Matrix.multiplyMM(mMvp, 0, mProjection, 0, mRotation, 0);
            GLES20.glUniformMatrix4fv(mMvpLoc, 1, false, mMvp, 0);
        }
        if (mScreenSizeLoc != -1) {
            float width = 480;
            float height = 800;
            Log.e("EllisMarkov", "To be done");
            GLES20.glUniform2f(mScreenSizeLoc, width, height);
        }
        if (mTimeLoc != -1) {
            float time = 0;
            Log.e("EllisMarkov", "To be done");
            GLES20.glUniform1f(mTimeLoc, time);
        }
    }

    public void draw(Vertices vertices, int type, int size) {
        enableVertexAttribArray(vertices);
        vertices.getIndices().position(0);
        GLES20.glDrawElements(type, size, GLES20.GL_UNSIGNED_SHORT, vertices.getIndices());
        disableVertexAttribArray();
    }

    private void enableVertexAttribArray(Vertices vertices) {
        if (mPositionLoc != -1) {
            vertices.getVertices().position(0);
            GLES20.glVertexAttribPointer(mPositionLoc, 3, GLES20.GL_FLOAT, false, P3FT2FR4FVertex.P3FT2FR4FVertex_SIZE_BYTES, vertices.getVertices());
            GLES20.glEnableVertexAttribArray(mPositionLoc);
        }
        if (mColorLoc != -1) {
            vertices.getVertices().position(5);
            GLES20.glVertexAttribPointer(mColorLoc, 4, GLES20.GL_FLOAT, false, P3FT2FR4FVertex.P3FT2FR4FVertex_SIZE_BYTES, vertices.getVertices());
            GLES20.glEnableVertexAttribArray(mColorLoc);
        }
        if (mTexCoordLoc != -1) {
            vertices.getVertices().position(3);
            GLES20.glVertexAttribPointer(mTexCoordLoc, 2, GLES20.GL_FLOAT, false, P3FT2FR4FVertex.P3FT2FR4FVertex_SIZE_BYTES, vertices.getVertices());
            GLES20.glEnableVertexAttribArray(mTexCoordLoc);
        }
    }

    private void disableVertexAttribArray() {
        if (mPositionLoc != -1) {
            GLES20.glDisableVertexAttribArray(mPositionLoc);
        }
        if (mColorLoc != -1) {
            GLES20.glDisableVertexAttribArray(mColorLoc);
        }
        if (mTexCoordLoc != -1) {
            GLES20.glDisableVertexAttribArray(mTexCoordLoc);
        }
    }

    private boolean link() {
        if (mProgramObject==0) {
            Log.e("EllisMarkov", "No GLSL Program created!");
            return false;
        }
        GLES20.glLinkProgram(mProgramObject);
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(mProgramObject, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] != GLES20.GL_TRUE) {
            Log.e("EllisMarkov", "Could not link program: ");
            Log.e("EllisMarkov", "logs:" + GLES20.glGetProgramInfoLog(mProgramObject));
            GLES20.glDeleteProgram(mProgramObject);
            mProgramObject = 0;
            return false;
        }
        return true;
    }

    private boolean loadShaders(String vertexFilename, String fragmentFilename) {
        if (mProgramObject==0) {
            Log.e("EllisMarkov", "No GLSL Program created!");
            return false;
        }

        // load vertex and fragment shader
        mVertexShader = loadShader(vertexFilename, GLES20.GL_VERTEX_SHADER);
        mFragmentShader = loadShader(fragmentFilename, GLES20.GL_FRAGMENT_SHADER);

        // if one of shader cannot be read return false
        if (mVertexShader==0 || mFragmentShader==0) {
            Log.e("EllisMarkov", "Shader doesn' compile");
            return false;
        }

        GLES20.glAttachShader(mProgramObject, mVertexShader);
        GLES20.glAttachShader(mProgramObject, mFragmentShader);
        return true;
    }

    /* load a Vertex or Fragment shader */
    private int loadShader(String filename, int shaderType) {
        String source = Util.readStringAsset(mActivity, filename);
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e("", "Could not compile shader " + shaderType + ":");
                Log.e("", GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        Log.i("EllisMarkov", "shader compiled:" + filename);
        return shader;
    }

}
