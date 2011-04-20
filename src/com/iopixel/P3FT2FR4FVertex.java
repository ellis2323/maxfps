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

