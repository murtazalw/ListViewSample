package com.example.murtaza.listviewsample;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;

/**
 * Created by Murtaza on 03-May-2016.
 */
public class ListViewSettingsActivity
        extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefsFragment()).commit();
    }
}
