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

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.*;

/* Support us on http://www.iopixel.com */
public class SystemInformation {

    static String suTestScript = "#!/system/bin/sh\necho ";
    static String script = "computeFreq.sh";
	static String suTestScriptValid = "SuPermsOkay";

    static public String getCurFreq(int id) {
        String maxFreq = "";

        try {
            String filename = "/sys/devices/system/cpu/cpu" + id + "/cpufreq/scaling_cur_freq";
            File file = new File(filename);
            if (!file.exists()) {
                filename = "/sys/devices/system/cpu/cpu" + id + "/cpufreq/cpuinfo_cur_freq";
                
            }
            BufferedReader reader = new BufferedReader(new FileReader(filename), 256);
            try {
                maxFreq = reader.readLine();
            } finally {
                reader.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        long freq = Long.parseLong(maxFreq);
        freq /= 1000;
        return "" + freq + "Mhz";
    }

    static public int getNbCore() {
        File cpu3 = new File("/sys/devices/system/cpu/cpu3");
        if (cpu3.exists() && cpu3.isDirectory()) {
            return 4;
        }
        File cpu2 = new File("/sys/devices/system/cpu/cpu2");
        if (cpu2.exists() && cpu2.isDirectory()) {
            return 3;
        }
        File cpu1 = new File("/sys/devices/system/cpu/cpu1");
        if (cpu1.exists() && cpu1.isDirectory()) {
            return 2;
        }
        return 1;
    }

    /* Code provided by supercurio */
    static public Boolean detectValidSuBinaryInPath() {
		String[] pathToTest = System.getenv("PATH").split(":");
		for (String path : pathToTest) {
			File suBinary = new File(path + "/su");
			if (suBinary.exists()) {
				try {
					Process process = Runtime.getRuntime().exec(
							"ls -l " + suBinary.getAbsolutePath());
					BufferedReader input = new BufferedReader(
							new InputStreamReader(process.getInputStream()));

					if (input.readLine().matches("^-rws.*root.*")) {
						Log.i("MaxFPS", "Found adequate su binary at "
                                + suBinary.getAbsolutePath());
						return true;
					}
				} catch (IOException e) {
				}
			}
		}
		return false;
	}

    /* Code provided by supercurio */
   	static public Boolean isSuperUserApkinstalled(Context context) {
		try {
			context.getPackageManager().getPackageInfo(
					"com.noshufou.android.su", 0);
			Log.i("VoodooApp", "Superuser.apk com.noshufou.android.su present");
			return true;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}

    /* Code provided by supercurio */
	static public Boolean isSuAvailable(Context context) {
		if (detectValidSuBinaryInPath() && isSuperUserApkinstalled(context)) {
			return true;
        }
		return false;
	}

    /* Code provided by supercurio */
    static public String canGainSuFor(Context context, String command) {
		Process process;
		Boolean result = false;

		try {
			FileOutputStream output = context.openFileOutput(script, Context.MODE_PRIVATE);
			output.write((command).getBytes());
			output.close();
			String fullCommandPath = context.getFileStreamPath(script).getAbsolutePath();

			// set permissions
			Runtime.getRuntime().exec("chmod 300 " + fullCommandPath);

			// run the command
			String cmd = "su -c " + fullCommandPath;
			Log.i("MaxFPS", cmd);
			process = Runtime.getRuntime().exec(cmd);

			// parse the output
			String line;
            String lines = "";
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = input.readLine()) != null) {
                lines += line;
			}
            int returnValue = process.exitValue();

			if (returnValue==0) {
				Log.i("MaxFPS", "Can run su commands:" +lines);
            } else {
                Log.i("MaxFPS", "Can't run su commands");
                return null;
            }

			input.close();
			new File(fullCommandPath).delete();

			return lines;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
