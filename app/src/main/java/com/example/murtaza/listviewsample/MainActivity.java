package com.example.murtaza.listviewsample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {

    //Variables for xml elements
    EditText inputSearch;
    ListView listView;
    SwipeRefreshLayout refreshLayout;

    //adapter for ListView
    ArrayAdapter<String> adapter, refreshedAdapter;

    private static final int RESULT_SETTINGS = 1;

    String[] products;
    ArrayList<String> list, refreshedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Get EditText object from xml
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);

        // Defined Array values to show in ListView
        products = new String[]{"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
                "iPhone 4S", "Samsung Galaxy Note 800",
                "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};

        list = new ArrayList<String>();
        Collections.addAll(list, products);

        refreshedList = list;

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);

        if (loadPrefs())
            listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();
            }
        });

        listView.setOnTouchListener(new OnSwipeTouchListener(this, listView, list) {

            @Override
            public void onSwipeRight(int pos) {
                swipe(pos, android.R.anim.slide_out_right);
            }

            @Override
            public void onSwipeLeft(int pos) {
                swipe(pos, android.R.anim.slide_in_left);
            }

            public void swipe(final int pos, int dir) {
                final Animation anim =
                        AnimationUtils.loadAnimation(MainActivity.this,
                                dir);
                anim.setDuration(1000);
                listView.getChildAt(pos).startAnimation(anim);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        list.remove(pos);
                        adapter.notifyDataSetChanged();
                        anim.cancel();
                    }
                }, 1000);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
        search(inputSearch);
    }

    private void refreshList() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                Collections.addAll(list, products);
                adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
                listView.setAdapter(adapter);
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings: {
                Intent i = new Intent(this, ListViewSettingsActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);
                break;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SETTINGS: {
                if (loadPrefs())
                    listView.setAdapter(adapter);   // Lists are shown
                else
                    listView.setAdapter(null);      // Lists are not shown
                break;
            }
        }
    }

    //get prefs
    private Boolean loadPrefs() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean isEnable = sp.getBoolean("pref_key_lists", true);
        return isEnable;
    }


    public void search(EditText text) {
        text.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                MainActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }
}
