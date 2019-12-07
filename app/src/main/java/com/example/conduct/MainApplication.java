package com.example.conduct;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.preference.PreferenceManager;

import com.example.conduct.utils.LocaleManager;

public class MainApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.onAttach(base, base.getResources().getString(R.string.language_english_value)));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.language_preference), getString(R.string.language_english_value)));
    }
}
