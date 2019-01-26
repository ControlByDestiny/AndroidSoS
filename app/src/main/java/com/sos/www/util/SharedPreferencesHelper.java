package com.sos.www.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SharedPreferencesHelper {
    private static final String TAG = "SharedPreferencesHelper";
    private Context context;
    private SharedPreferences prefrence;

    public SharedPreferencesHelper(Context context) {
        this.context = context.getApplicationContext();
    }

    public void open(String name) {
        this.open(name, 0);
    }

    public void open(String name, int version) {
        String fileName = name + "_" + version;
        this.prefrence = this.context.getSharedPreferences(fileName, 0);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = this.prefrence.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return this.prefrence.getString(key, "");
    }

    public void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = this.prefrence.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return this.prefrence.getBoolean(key, false);
    }


    public boolean getBoolean(String key, boolean defValue) {
        return this.prefrence.getBoolean(key, defValue);
    }

    public void putLong(String key, Long value) {
        SharedPreferences.Editor editor = this.prefrence.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(String key) {
        return this.prefrence.getLong(key, 0L);
    }

    public void putInt(String key, Integer value) {
        SharedPreferences.Editor editor = this.prefrence.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key) {
        return this.prefrence.getInt(key, -1);
    }

    public void putFloat(String key, Float value) {
        SharedPreferences.Editor editor = this.prefrence.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public float getFloat(String key) {
        return this.prefrence.getFloat(key, 0.0F);
    }

    public void put(String key, Object value) {
        if(value != null) {
            try {
                ByteArrayOutputStream t = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(t);
                oos.writeObject(value);
                oos.flush();
                oos.close();
                byte[] data = t.toByteArray();
                String base64 = Base64.encodeToString(data, 2);
                this.putString(key, base64);
            } catch (Throwable var7) {
                Log.w(TAG, var7.toString());
            }

        }
    }

    public Object get(String key) {
        try {
            String t = this.getString(key);
            if(TextUtils.isEmpty(t)) {
                return null;
            } else {
                byte[] data = Base64.decode(t, 2);
                ByteArrayInputStream bais = new ByteArrayInputStream(data);
                ObjectInputStream ois = new ObjectInputStream(bais);
                Object value = ois.readObject();
                ois.close();
                return value;
            }
        } catch (Throwable var7) {
            Log.w(TAG, var7.toString());
            return null;
        }
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = this.prefrence.edit();
        editor.remove(key);
        editor.apply();
    }
}
