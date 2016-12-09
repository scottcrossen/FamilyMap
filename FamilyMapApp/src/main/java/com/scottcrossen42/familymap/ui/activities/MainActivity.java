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
        // Super-class the create method:
        super.onCreate(savedInstanceState);
        //Setup the layout:
        setContentView(R.layout.activity_main);
        FragmentManager fm = this.getSupportFragmentManager();
        LoginFragment login_fragment = (LoginFragment) fm.findFragmentById(R.id.mainFrameLayout);
        // Should I start the layout?
        if (login_fragment == null) startLoginFragment();
        //Default behavior
        menu_enabled = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // What the menu looks like and all the icons:
        menu_search = menu.findItem(R.id.main_menu_search).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_search).colorRes(R.color.MenuIcons).sizeDp(20));
        menu_filter = menu.findItem(R.id.main_menu_filter).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_filter).colorRes(R.color.MenuIcons).sizeDp(20));
        menu_settings = menu.findItem(R.id.main_menu_settings).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_gear).colorRes(R.color.MenuIcons).sizeDp(20));
        // Refresh this business:
        refreshMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // This class calls the recycler. This needs to be implemented:
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
        // If the fragment is a login fragment then we can start the next fragment.
        if(fragment.getClass()==LoginFragment.class && fragmentIntent.getAction() == "fragment finished") Model.getInstance().syncData(this);
        // What if the fragment encounters an error?
        if(fragment.getClass()==LoginFragment.class && fragmentIntent.getAction() == "http error") {
            Log.e(Constants.TAG, "Unable to retrieve data. " + fragmentIntent.getStringExtra("error"));
            Toast.makeText(this, "ERROR: unable to retrieve data.", Toast.LENGTH_LONG).show();
        }
        // This method is depricated but I might as well keep it here:
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

    @Override
    public void onResume() {
        // When this activity is called back:
        super.onResume();
        if (!ServerSession.getInstance().isLoggedOn()) {
            startLoginFragment();
        }
        else startMapFragment();
    }

    private void startSearchActivity() { // This method is good to have but I don't use it.
        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);
    }

    private void startFilterActivity() { // This method is good to have but I don't use it.
        Intent i = new Intent(this, FilterActivity.class);
        startActivity(i);
    }

    private void startSettingsActivity() { // This method is good to have but I don't use it.
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    private void startLoginFragment() { // This starts the login portion.
        disableMenu();
        FragmentManager fm = this.getSupportFragmentManager();
        LoginFragment login_fragment = LoginFragment.newInstance();
        login_fragment.setParent(this);
        fm.beginTransaction().replace(R.id.mainFrameLayout, login_fragment).commit();
    }

    private void startMapFragment() { // This starts the actual map.
        enableMenu();
        FragmentManager fm = this.getSupportFragmentManager();
        FamilyMapFragment map_fragment = FamilyMapFragment.newInstance();
        map_fragment.setParent(this);
        fm.beginTransaction().replace(R.id.mainFrameLayout, map_fragment).commit();
    }

    private void enableMenu() { // Enables menu
        menu_enabled = true;
        refreshMenu();
    }

    private void disableMenu() { // Disables Menu
        menu_enabled = false;
        refreshMenu();
    }

    private void refreshMenu() { // Refreshes menu. Duh.
        if (menu_search != null) menu_search.setVisible(menu_enabled);
        if (menu_filter != null) menu_filter.setVisible(menu_enabled);
        if (menu_settings != null) menu_settings.setVisible(menu_enabled);
    }
}