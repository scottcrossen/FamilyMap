package com.scottcrossen42.familymap.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by Scott Leland Crossen
 */
public interface IFragmentCaller {
    public abstract void fragmentAction(Fragment fragment, Intent fragmentIntent);
}
