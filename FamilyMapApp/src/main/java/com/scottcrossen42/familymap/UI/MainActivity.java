package com.scottcrossen42.familymap.UI;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.scottcrossen42.familymap.R;

public class MainActivity extends AppCompatActivity {
    private LoginFragment loginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = this.getSupportFragmentManager();
        startLoginFragment();
    }
    private void startLoginFragment() {
        FragmentManager fm = this.getSupportFragmentManager();
        LoginFragment login_fragment = LoginFragment.newInstance();
        fm.beginTransaction().replace(R.id.mainFrameLayout, login_fragment).commit();
    }
}
