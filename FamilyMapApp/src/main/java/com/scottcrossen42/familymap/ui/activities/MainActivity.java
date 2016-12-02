package com.scottcrossen42.familymap.ui.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.scottcrossen42.familymap.R;
import com.scottcrossen42.familymap.httpaccess.ServerSession;
import com.scottcrossen42.familymap.ui.fragments.LoginFragment;
import com.scottcrossen42.familymap.ui.fragments.MapFragment;

public class MainActivity extends AppCompatActivity implements IFragmentCaller {
    private boolean menu_enabled = false;
    MenuItem menu_search;
    MenuItem menu_filter;
    MenuItem menu_settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = this.getSupportFragmentManager();
        startLoginFragment();
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
        MapFragment map_fragment = MapFragment.newInstance();
        fm.beginTransaction().replace(R.id.mainFrameLayout, map_fragment).commit();
    }
    @Override
    public void fragmentAction(android.support.v4.app.Fragment fragment, Intent fragmentIntent) {
        if(fragment.getClass()==LoginFragment.class && fragmentIntent.getAction() == "fragment finished") /*Model.getInstance().syncData(this)*/;
        if(fragment.getClass()==MapFragment.class && fragmentIntent.getAction() == "fragment finished") ;
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
        if (!ServerSession.getInstance().isLoggedOn())
            startLoginFragment();
    }
}
