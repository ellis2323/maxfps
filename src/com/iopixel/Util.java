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

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class Util {

    /** read an asset file as Text File and return a string */
    public static String readStringAsset(Context context, String filename) {
        try {
            InputStream iStream = context.getAssets().open(filename);
            return readStringInput(iStream);
        } catch (IOException e) {
            Log.e("EllisMarkov", "Shader " + filename + " cannot be read");
            return "";
        }
    }

    /** read string input stream */
    public  static String readStringInput(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();

        byte[] buffer = new byte[4096];
        for (int n; (n = in.read(buffer)) != -1;) {
            sb.append(new String(buffer, 0, n));
        }
        return sb.toString();
    }


}
