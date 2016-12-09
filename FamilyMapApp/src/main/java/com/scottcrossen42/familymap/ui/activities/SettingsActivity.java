package com.scottcrossen42.familymap.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.scottcrossen42.familymap.Constants;
import com.scottcrossen42.familymap.R;
import com.scottcrossen42.familymap.httpaccess.ServerSession;
import com.scottcrossen42.familymap.model.Model;
import com.scottcrossen42.familymap.model.Settings;

public class SettingsActivity extends AppCompatActivity implements ITaskCaller {

    private Settings settings = Settings.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the super 'constructor'
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Setup the spinners on the activity
        // This is for the life-life color
        Spinner spinner_story = (Spinner)findViewById(R.id.LifeStorySpinner);
        spinner_story.setAdapter(new ArrayAdapter<Settings.LineColor>(this, android.R.layout.simple_spinner_item, Settings.LineColor.values()));
        spinner_story.setSelection(settings.getLifeLineColor().getIndex());
        spinner_story.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Settings.LineColor color = (Settings.LineColor) parent.getItemAtPosition(pos);
                settings.setLifeLineColor(color);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        // This is for the family tree spinner
        Spinner spinner_tree = (Spinner)findViewById(R.id.FamilyTreeSpinner);
        spinner_tree.setAdapter(new ArrayAdapter<Settings.LineColor>(this, android.R.layout.simple_spinner_item, Settings.LineColor.values()));
        spinner_tree.setSelection(settings.getFamilyTreeColor().getIndex());
        spinner_tree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Settings.LineColor color = (Settings.LineColor) parent.getItemAtPosition(pos);
                settings.setFamilyTreeColor(color);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        // This is for the spouse spinner
        Spinner spinner_spouse = (Spinner)findViewById(R.id.SpouseSpinner);
        spinner_spouse.setAdapter(new ArrayAdapter<Settings.LineColor>(this, android.R.layout.simple_spinner_item, Settings.LineColor.values()));
        spinner_spouse.setSelection(settings.getSpouseLineColor().getIndex());
        spinner_spouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Settings.LineColor color = (Settings.LineColor) parent.getItemAtPosition(pos);
                settings.setSpouseLineColor(color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        // This is for the background spinner
        Spinner spinner_map = (Spinner)findViewById(R.id.BackgroundSpinner);
        spinner_map.setAdapter(new ArrayAdapter<Settings.MapBackground>(this, android.R.layout.simple_spinner_item, Settings.MapBackground.values()));
        spinner_map.setSelection(settings.getMapBackground().getIndex());
        spinner_map.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Settings.MapBackground bg = (Settings.MapBackground) parent.getItemAtPosition(pos);
                settings.setMapBackground(bg);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        // This is for the sync task
        LinearLayout sync_layout = (LinearLayout)findViewById(R.id.SyncRow);
        sync_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSyncClicked();
            }
        });
        // This is for the logout task
        LinearLayout logout_layout = (LinearLayout)findViewById(R.id.LogoutRow);
        logout_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogoutClicked();
            }
        });
        // This is for the life-story switch
        Switch life_story_toggle = (Switch)findViewById(R.id.LifeStoryToggle);
        life_story_toggle.setChecked(settings.isLifeLineEnabled());
        life_story_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setLifeLine(isChecked);
            }
        });
        // This is for the family tree switch
        Switch family_tree_toggle = (Switch)findViewById(R.id.FamilyTreeToggle);
        family_tree_toggle.setChecked(settings.isFamilyTreeEnabled());
        family_tree_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setFamilyTree(isChecked);
            }
        });
        // This is for the spouse switch
        Switch spouse_toggle = (Switch)findViewById(R.id.SpouseToggle);
        spouse_toggle.setChecked(settings.isSpouseLineEnabled());
        spouse_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setSpouseLine(isChecked);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // required by the appcompatactivity.
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onLogoutClicked() {
        ServerSession.getInstance().clearLogin();
        goToTop();
    }

    private void goToTop() {
        // Menu option
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void syncAction(Intent intent) {
        // This is the method to access the async task.
        if(intent.getAction() == "sync done") {
            Toast.makeText(this, "Sync Finished", Toast.LENGTH_LONG).show();
            goToTop();
        }
        if(intent.getAction() == "http error") {
            Log.e(Constants.TAG, "Unable to retrieve data. " + intent.getStringExtra("error"));
            Toast.makeText(this, "ERROR: unable to retrieve data.", Toast.LENGTH_LONG).show();
        }
    }

    private void onSyncClicked() { Model.getInstance().syncData(this); }
}
