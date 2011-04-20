package com.ellismarkov;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * This is not optimized (P3FT2FR4FVertex=>36octets)
 * It is should be better to use RGBA int byte (but Painful).
 * In C, it is trivial because we have pointers
 */
public class P3FT2FR4FVertex {

    public final static int P3FT2FR4FVertex_SIZE = 9;
    public final static int P3FT2FR4FVertex_SIZE_BYTES = 9*4;

    public float mX, mY, mZ;
    public float mU, mV;
    public float mR, mG, mB, mA;

    public P3FT2FR4FVertex() {
        mX = mY = mZ = 0;
        mU = mV = 0;
        mR = mG = mB = mA = 0;
    }

    public void setPos(float x, float y, float z) {
        mX = x;
        mY = y;
        mZ = z;
    }

    public void setTcoord(float u, float v) {
        mU = u;
        mV = v;
    }

    public void setColor(float r, float g, float b, float a) {
        mR = r;
        mG = g;
        mB = b;
        mA = a;
    }
}

