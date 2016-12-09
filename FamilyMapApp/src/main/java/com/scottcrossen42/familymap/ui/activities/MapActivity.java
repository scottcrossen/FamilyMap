package com.scottcrossen42.familymap.ui.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.scottcrossen42.familymap.Constants;
import com.scottcrossen42.familymap.R;
import com.scottcrossen42.familymap.model.Event;
import com.scottcrossen42.familymap.model.Model;
import com.scottcrossen42.familymap.ui.fragments.FamilyMapFragment;

public class MapActivity extends AppCompatActivity {

    Model model = Model.getInstance();
    Event selected_event;


    @Override
    protected void onCreate(Bundle savedInstanceState) { // Overrided method
        // Calls super method
        super.onCreate(savedInstanceState);
        // Setups layout
        setContentView(R.layout.activity_map);
        // refers to the calling intent
        Intent i = getIntent();
        String event_id = i.getStringExtra(Constants.MAP_ACTIVITY_ARG_1);
        // Extracts event
        selected_event = model.getEvent(event_id);
        // setups the rest of the layout
        FragmentManager fm = this.getSupportFragmentManager();
        FamilyMapFragment familymap_fragment = FamilyMapFragment.newInstance();
        fm.beginTransaction().replace(R.id.familyMapFrameLayout, familymap_fragment).commit();
        familymap_fragment.setSelectedEvent(selected_event);
        // Misc setup.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // setups menu
        getMenuInflater().inflate(R.menu.menu_map, menu);
        MenuItem menu_top = menu.findItem(R.id.map_menu_top).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_angle_double_up).colorRes(R.color.MenuIcons).sizeDp(20));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Overrided to refer to the menu
        switch (item.getItemId()) {
            case R.id.map_menu_top:
                goToTop();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToTop() { // This is the button on the menu.
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
