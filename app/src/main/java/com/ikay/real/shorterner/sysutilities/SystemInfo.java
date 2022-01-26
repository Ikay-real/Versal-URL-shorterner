package com.ikay.real.shorterner.sysutilities;

import android.os.Build;

public class SystemInfo {
    static String manufacturer = Build.MANUFACTURER;
    static String model = Build.MODEL;
    static String versionRelease = Build.VERSION.RELEASE;
    static int version = Build.VERSION.SDK_INT;
    public static String getVersionRelease() {
        return versionRelease;
    }

    public static String getModel() {
        return model;
    }

    public static int getVersion() {
        return version;
    }



    public static String getManufacturer() {
        return manufacturer;
    }
}
