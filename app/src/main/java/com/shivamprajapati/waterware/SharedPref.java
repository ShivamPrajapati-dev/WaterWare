package com.shivamprajapati.waterware;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPref {
    public static String fileName="checkLogin";


    public static boolean readSharedSettingsIsLogin(Context context, String settingName, boolean defaultValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(settingName,defaultValue);
    }

    public static boolean readSharedSettingsIntroSlider(Context context, String settingName, boolean defaultValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(settingName,defaultValue);
    }

    public static boolean readSharedSettingsFirstOnBoarding(Context context, String settingName, boolean defaultValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(settingName,defaultValue);
    }

    public static boolean readSharedSettingsThirdOnBoarding(Context context, String settingName, boolean defaultValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(settingName,defaultValue);
    }

    public static boolean readSharedSettingsSecondOnBoarding(Context context, String settingName, boolean defaultValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(settingName,defaultValue);
    }



    public static boolean readSharedSettingsIsThereAPendingRequest(Context context, String settingName, boolean defaultValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(settingName,defaultValue);
    }

    public static String readSharedSettingsMyToken(Context context, String settingName, String defaultValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }


    public static String readSharedSettingsName(Context context, String settingName, String defaultValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }

    public static String readSharedSettingsIsPhoneVerified(Context context, String settingName, String defaultValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }

    public static String readSharedSettingsEmail(Context context, String settingName, String defaultValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }

    public static String readSharedSettingsPhoneNumber(Context context, String settingName, String defaultValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }

    public static String readSharedSettingsAddress(Context context, String settingName, String defaultValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }



    public static boolean readSharedSettingsIsLoginPhone(Context context, String settingName, boolean defaultValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(settingName,defaultValue);
    }
    public static boolean readSharedSettingsIsLoginGoogle(Context context, String settingName, boolean defaultValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(settingName,defaultValue);
    }

    public static boolean readSharedSettingsIsDataCreated(Context context,String settingName,boolean defaultValue){

        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(settingName,defaultValue);
    }
    public static boolean readSharedSettingsIsDataCreatedPhone(Context context,String settingName,boolean defaultValue){

        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(settingName,defaultValue);
    }
    public static boolean readSharedSettingsIsDataCreatedGoogle(Context context,String settingName,boolean defaultValue){

        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(settingName,defaultValue);
    }

    public static String readSharedSettingsPendingId(Context context,String settingName,String defaultValue){

        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }
    public static String readSharedSettingsId(Context context,String settingName,String defaultValue){

        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }
    public static String readSharedSettingsToken(Context context,String settingName,String defaultValue){

        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }

    public static String readSharedSettingsPreviousHistoryList(Context context,String settingName,String defaultValue){

        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return  sharedPreferences.getString(settingName,defaultValue);
    }

    public static String readSharedSettingsNotificationList(Context context,String settingName,String defaultValue){

        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return  sharedPreferences.getString(settingName,defaultValue);
    }

    public static void saveSharedSettingsIsLogin(Context context,String settingName,boolean settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(settingName,settingValue).commit();
    }

    public static void saveSharedSettingsIsThereAPendingRequest(Context context,String settingName,boolean settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(settingName,settingValue).commit();
    }

    public static void saveSharedSettingsIntroSlider(Context context,String settingName,boolean settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(settingName,settingValue).commit();
    }

    public static void saveSharedSettingsFirstOnBoarding(Context context,String settingName,boolean settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(settingName,settingValue).commit();
    }

    public static void saveSharedSettingsSecondOnBoarding(Context context,String settingName,boolean settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(settingName,settingValue).commit();
    }
    public static void saveSharedSettingsThirdOnBoarding(Context context,String settingName,boolean settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(settingName,settingValue).commit();
    }

    public static void saveSharedSettingsIsLoginPhone(Context context,String settingName,boolean settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(settingName,settingValue).commit();
    }

    public static void saveSharedSettingsIsLoginGoogle(Context context,String settingName,boolean settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(settingName,settingValue).commit();
    }

    public static void saveSharedSettingsIsDataCreated(Context context,String settingName,boolean settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(settingName,settingValue).commit();
    }
    public static void saveSharedSettingsIsDataCreatedPhone(Context context,String settingName,boolean settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(settingName,settingValue).commit();
    }
    public static void saveSharedSettingsIsDataCreatedGoogle(Context context,String settingName,boolean settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(settingName,settingValue).commit();
    }

    public static void saveSharedSettingsName(Context context,String settingName,String settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(settingName,settingValue).commit();
    }

    public static void saveSharedSettingsToken(Context context,String settingName,String settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(settingName,settingValue).commit();
    }

    public static void saveSharedSettingsEmail(Context context,String settingName,String settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(settingName,settingValue).commit();
    }

    public static void saveSharedSettingsPhoneNumber(Context context,String settingName,String settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(settingName,settingValue).commit();
    }

    public static void saveSharedSettingsPendingId(Context context,String settingName,String settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(settingName,settingValue).commit();
    }

    public static void saveSharedSettingsId(Context context,String settingName,String settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(settingName,settingValue).commit();
    }

    public static void saveSharedSettingsAddress(Context context,String settingName,String settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(settingName,settingValue).commit();
    }

    public static void saveSharedSettingPreviousHistoryList(Context context, String settingName, String historyList){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(settingName,historyList).commit();
    }

    public static void saveSharedSettingNotificationList(Context context, String settingName, String historyList){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(settingName,historyList).commit();
    }

    public static void saveSharedSettingIsPhoneVerified(Context context, String settingName, String settingvalue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(settingName,settingvalue).commit();
    }

    public static void saveSharedSettingMyToken(Context context, String settingName, String settingvalue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(settingName,settingvalue).commit();
    }

    public static void delete(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear().apply();


    }

}
