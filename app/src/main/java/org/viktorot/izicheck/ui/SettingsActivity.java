package org.viktorot.izicheck.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.viktorot.izicheck.R;
import org.viktorot.izicheck.Storage;


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, SettingsFragment.newInstance())
                .commit();
    }
}
