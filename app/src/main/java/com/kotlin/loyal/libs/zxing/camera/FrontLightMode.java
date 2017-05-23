package com.kotlin.loyal.libs.zxing.camera;

import android.content.SharedPreferences;

import com.kotlin.loyal.libs.zxing.PreferencesActivity;

/**
 * Enumerates settings of the preference controlling the front light.
 */
public enum FrontLightMode {

    /**
     * Always on.
     */
    ON,
    /**
     * On only when ambient light is low.
     */
    AUTO,
    /**
     * Always off.
     */
    OFF;

    private static FrontLightMode parse(String modeString) {
        return modeString == null ? OFF : valueOf(modeString);
    }

    public static FrontLightMode readPref(SharedPreferences sharedPrefs) {
        return parse(sharedPrefs.getString(PreferencesActivity.KEY_FRONT_LIGHT_MODE, OFF.toString()));
    }
}
