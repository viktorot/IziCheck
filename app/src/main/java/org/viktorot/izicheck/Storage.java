package org.viktorot.izicheck;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class Storage {

    public static final String NAME = "org.viktorot.izicheck.PREFERENCE";

    private final String KEY_PHONE_NUMBER;
    private final String KEY_PUK;

    private SharedPreferences sharedPreferences;

    public Storage(@NonNull Context context) {
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        KEY_PHONE_NUMBER = context.getString(R.string.pref_key_phone_number);
        KEY_PUK = context.getString(R.string.pref_key_puk);
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    public void savePhoneNumber(@NonNull String number) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(KEY_PHONE_NUMBER, number);
        editor.apply();
    }

    @NonNull
    public String getPhoneNumber() {
        return sharedPreferences.getString(KEY_PHONE_NUMBER, BuildConfig.DEFAULT_PHONE_NUMBER);
    }

    public void savePuk(@NonNull String puk) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(KEY_PUK, puk);
        editor.apply();
    }

    @NonNull
    public String getPuk() {
        return sharedPreferences.getString(KEY_PUK, BuildConfig.DEFAULT_PUK);
    }

}
