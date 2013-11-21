package com.example.RssReader.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.RssReader.R;
import com.example.RssReader.Utils;
import com.example.RssReader.services.RssService;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 11/20/13
 * Time: 9:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertiesActivity extends Activity {
    TextView tvLastDate;
    CheckBox checkEnableService;
    Spinner spFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.properties_activity);
        init();
        bindActions();
        setData();
    }

    private void init() {
        tvLastDate = (TextView) findViewById(R.id.tvLastUpdate);
        checkEnableService = (CheckBox) findViewById(R.id.chkEnableService);
        spFrequency = (Spinner) findViewById(R.id.spMinutes);
    }

    private void setData() {
        tvLastDate.setText(Utils.getLastUpdateData(this));
        int currentPosition = Utils.getFrequencyPosition(PropertiesActivity.this);
        spFrequency.setSelection(currentPosition, true);
    }

    private void bindActions() {
        checkEnableService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    Intent intent = new Intent(PropertiesActivity.this, RssService.class);
                    startService(intent);
                } else {
                    Intent intent = new Intent(PropertiesActivity.this, RssService.class);
                    stopService(intent);
                }
            }
        });

        spFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Utils.setFrequencyPosition(PropertiesActivity.this, position);
                Utils.setFrequencyMin(PropertiesActivity.this, Integer.valueOf(parent.getItemAtPosition(position).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

}
