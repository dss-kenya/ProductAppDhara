package com.agro.star.dhara.productapp.utils;

import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.agro.star.dhara.productapp.ProductApp;
import com.agro.star.dhara.productapp.R;
import com.agro.star.dhara.productapp.utils.prefs.SharedPrefs;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Created by Dhara Shah on 23-03-2016.
 */
public class CommonUtilities {
    /**
     * Changes the keyboard settings and also the locale
     * @return
     */
    public static boolean changeKeyBoardSettings() {
        String currentLocaleString = SharedPrefs.getString(SharedPrefs.LANGUAGE_SELECTED,
                ProductApp.getAppContext().getString(R.string.english_code));

        DisplayMetrics displayMetrics = ProductApp.getAppContext().getResources().getDisplayMetrics();
        Configuration configuration = ProductApp.getAppContext().getResources().getConfiguration();
        configuration.locale = new Locale(currentLocaleString.toLowerCase());
        ProductApp.getAppContext().getResources().updateConfiguration(configuration,displayMetrics);
        return true;
    }
}
