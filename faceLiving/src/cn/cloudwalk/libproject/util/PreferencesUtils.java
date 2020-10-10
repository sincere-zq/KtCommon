package cn.cloudwalk.libproject.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesUtils {



    
   
    public static boolean putString(Context context, String key, String value) {
      SharedPreferences settings =   PreferenceManager.getDefaultSharedPreferences(context);
      
      SharedPreferences.Editor editor = settings.edit();
      editor.putString(key, value);
      return editor.commit();
    }

    public static String getString(Context context, String key ) {
      SharedPreferences settings =   PreferenceManager.getDefaultSharedPreferences(context);
      return settings.getString(key, null);
  }
    public static String getString(Context context, String key,String defaultValue ) {
    	SharedPreferences settings =   PreferenceManager.getDefaultSharedPreferences(context);
    	return settings.getString(key, defaultValue);
    }
    
    //
    public static boolean putLong(Context context, String key, long value) {
      SharedPreferences settings =   PreferenceManager.getDefaultSharedPreferences(context);

      SharedPreferences.Editor editor = settings.edit();
      editor.putLong(key, value);
      return editor.commit();
  }
    public static boolean putFloat(Context context, String key, float value) {
      SharedPreferences settings =   PreferenceManager.getDefaultSharedPreferences(context);
      
      SharedPreferences.Editor editor = settings.edit();
      editor.putFloat(key, value);
      return editor.commit();
    }
    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences settings =   PreferenceManager.getDefaultSharedPreferences(context);
        
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
      }
    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings =   PreferenceManager.getDefaultSharedPreferences(context);
        
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
      }
    
    public static long getLong(Context context, String key) {
      return getLong(context, key, -1);
    }

   
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences settings =   PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getInt(key, defaultValue);
    }
    public static float getFloat(Context context, String key, float defaultValue) {
    	SharedPreferences settings =   PreferenceManager.getDefaultSharedPreferences(context);
    	return settings.getFloat(key, defaultValue);
    }
    public static long getLong(Context context, String key, long defaultValue) {
    	SharedPreferences settings =   PreferenceManager.getDefaultSharedPreferences(context);
    	return settings.getLong(key, defaultValue);
    }
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
    	SharedPreferences settings =   PreferenceManager.getDefaultSharedPreferences(context);
    	return settings.getBoolean(key, defaultValue);
    }
   
    
   
    public static boolean removeSharedPreferenceByKey(Context context, String key){
        SharedPreferences settings =   PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        return editor.commit();
    }
}
