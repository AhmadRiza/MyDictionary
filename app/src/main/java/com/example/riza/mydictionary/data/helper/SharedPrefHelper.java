package com.example.riza.mydictionary.data.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by riza on 24/08/18.
 */

public class SharedPrefHelper {

    private static final String DICTIONARY_PREFS = "dictprefs";
    private static final String FIRST_LAUNCH = "fLaunch";
    private SharedPreferences mPreferences;

    public SharedPrefHelper(Context context) {
        mPreferences = context.getSharedPreferences(DICTIONARY_PREFS,Context.MODE_PRIVATE);
    }

    public void setFirstLaunch(boolean b){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(FIRST_LAUNCH, b);
        editor.apply();
    }

    public boolean isFirstLaunch(){
        return mPreferences.getBoolean(FIRST_LAUNCH, true);
    }

}
