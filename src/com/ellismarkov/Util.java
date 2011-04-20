package com.ellismarkov;

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
