package com.agro.star.dhara.productapp.utils.prefs;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.agro.star.dhara.productapp.ProductApp;

/**
 * Sharedpreference class that stores the values in the sharedpreference
 * related to the app
 * @author Dhara Shah
 *
 */
public class SharedPrefs {
	public static final String LANGUAGE_SELECTED = "language_selected";
	private static final String PREFS_NAME="ProductApp";
	
	public static SharedPreferences getPrefs() {
		return ProductApp.getAppContext().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
	}
	
	public static String getString(String key, String defaultValue) {
		SharedPreferences sp = getPrefs();
		return sp.getString(key, defaultValue);
	}
	
	public static boolean getBoolean(String key, boolean defaultValue) {
		SharedPreferences sp = getPrefs();
		return sp.getBoolean(key, defaultValue);
	}
	
	public static int getInt(String key, int defaultValue) {
		SharedPreferences sp = getPrefs();
		return sp.getInt(key, defaultValue);
	}
	
	public static void putString(String key, String value) {
		SharedPreferences sp = getPrefs();
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static void putInt(String key, int value) {
		SharedPreferences sp = getPrefs();
		Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static void putStrings(String[] keys, String[] values) {
		SharedPreferences sp = getPrefs();
		Editor editor = sp.edit();
		
		for(int i=0;i<keys.length;i++) {
			String key = keys[i];
			String value = values[i];
			editor.putString(key, value);
		}
		editor.commit();
	}
	
	public static void putBoolean(String key, boolean value) {
		SharedPreferences sp = getPrefs();
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static void clearPrefs() {
		SharedPreferences sp = getPrefs();
		Editor editor = sp.edit();
		editor.commit();
	}
}
