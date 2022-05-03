package com.collegeproject.personx.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceClass {
  private static final String USER_PREF = "user@PersonX";
  private final SharedPreferences sharedPreferences;
  private final SharedPreferences.Editor sharedPreferencesEditor;
  
  public SharedPreferenceClass(Context context) {
    sharedPreferences = context.getSharedPreferences(USER_PREF, Activity.MODE_PRIVATE);
    this.sharedPreferencesEditor = sharedPreferences.edit();
  }
  
  public void setValueInt(String key, int value){
    sharedPreferencesEditor.putInt(key, value).commit();
  }
  
  public int getValueInt(String key) {
    return sharedPreferences.getInt(key, 0);
  }
  
  public void setValueString(String key, String value){
    sharedPreferencesEditor.putString(key, value).commit();
  }
  
  public String getValueString(String key) {
    return sharedPreferences.getString(key, "");
  }
  
  public void setValueBoolean(String key, Boolean value){
    sharedPreferencesEditor.putBoolean(key, value).commit();
  }
  
  public boolean getValueBoolean(String key) {
    return sharedPreferences.getBoolean(key, false);
  }
  
  public void clear() {
    sharedPreferencesEditor.clear().commit();
  }
}
