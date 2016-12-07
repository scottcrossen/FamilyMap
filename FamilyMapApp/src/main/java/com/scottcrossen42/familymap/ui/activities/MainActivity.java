package com.scottcrossen42.familymap.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.scottcrossen42.familymap.Constants;
import com.scottcrossen42.familymap.R;
import com.scottcrossen42.familymap.httpaccess.ServerSession;
import com.scottcrossen42.familymap.model.Model;
import com.scottcrossen42.familymap.ui.fragments.LoginFragment;
import com.scottcrossen42.familymap.ui.fragments.FamilyMapFragment;

/**
 * @author Scott Leland Crossen
 * @link http://scottcrossen42.com
 * Created on 12/1/16.
 */
public class MainActivity extends AppCompatActivity implements IFragmentCaller, ITaskCaller {
    private boolean menu_enabled = false;
    MenuItem menu_search;
    MenuItem menu_filter;
    MenuItem menu_settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = this.getSupportFragmentManager();
        LoginFragment login_fragment = (LoginFragment) fm.findFragmentById(R.id.mainFrameLayout);
        if (login_fragment == null) {
            startLoginFragment();
        }
        menu_enabled = false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu_search = menu.findItem(R.id.main_menu_search).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_search).colorRes(R.color.MenuIcons).sizeDp(20));
        menu_filter = menu.findItem(R.id.main_menu_filter).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_filter).colorRes(R.color.MenuIcons).sizeDp(20));
        menu_settings = menu.findItem(R.id.main_menu_settings).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_gear).colorRes(R.color.MenuIcons).sizeDp(20));

        refreshMenu();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_search:
                startSearchActivity();
                return true;
            case R.id.main_menu_filter:
                startFilterActivity();
                return true;
            case R.id.main_menu_settings:
                startSettingsActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void fragmentAction(android.support.v4.app.Fragment fragment, Intent fragmentIntent) {
        if(fragment.getClass()==LoginFragment.class && fragmentIntent.getAction() == "fragment finished") Model.getInstance().syncData(this);
        if(fragment.getClass()==FamilyMapFragment.class && fragmentIntent.getAction() == "event selected") {
            Intent i = new Intent(this, MapActivity.class);
            i.putExtra(Constants.MAP_ACTIVITY_ARG_1, fragmentIntent.getStringExtra("target event id"));
            startActivity(i);
        }
    }
    @Override
    public void syncAction(Intent intent) {
        if(intent.getAction() == "sync done") startMapFragment();
        if(intent.getAction() == "http error") {
            Log.e(Constants.TAG, "Unable to retrieve data. " + intent.getStringExtra("error"));
            Toast.makeText(this, "ERROR: unable to retrieve data.", Toast.LENGTH_LONG).show();
        }
    }

    private void startSearchActivity()
    {
        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);
    }

    private void startFilterActivity()
    {
        Intent i = new Intent(this, FilterActivity.class);
        startActivity(i);
    }

    private void startSettingsActivity()
    {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    private void startLoginFragment() {
        disableMenu();
        FragmentManager fm = this.getSupportFragmentManager();
        LoginFragment login_fragment = LoginFragment.newInstance();
        login_fragment.setParent(this);
        fm.beginTransaction().replace(R.id.mainFrameLayout, login_fragment).commit();
    }
    private void startMapFragment() {
        enableMenu();
        FragmentManager fm = this.getSupportFragmentManager();
        FamilyMapFragment map_fragment = FamilyMapFragment.newInstance();
        map_fragment.setParent(this);
        fm.beginTransaction().replace(R.id.mainFrameLayout, map_fragment).commit();
    }
    private void enableMenu() {
        menu_enabled = true;
        refreshMenu();
    }
    private void disableMenu() {
        menu_enabled = false;
        refreshMenu();
    }
    private void refreshMenu() {
        if (menu_search != null) menu_search.setVisible(menu_enabled);
        if (menu_filter != null) menu_filter.setVisible(menu_enabled);
        if (menu_settings != null) menu_settings.setVisible(menu_enabled);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!ServerSession.getInstance().isLoggedOn()) {
            startLoginFragment();
        }
        else startMapFragment();
    }
}
//TODO: Follow the milestones: https://docs.google.com/document/d/1YV0494viqVvGq67R5rodwnS0HrFsH4T2zdi6pd_rHmA/edit
//TODO: Here's the specs: https://students.cs.byu.edu/~cs240ta/fall2016/projects/family-map/FamilyMapSpecification.pdf
//TODO: Here's the course website: https://students.cs.byu.edu/~cs240ta/fall2016/projects/
//TODO: Add Back Buttons