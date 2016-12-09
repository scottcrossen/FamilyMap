package com.scottcrossen42.familymap.ui.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * @author Scott Leland Crossen
 * @link http://scottcrossen42.com
 * Created on 12/1/16.
 */
public interface ITaskCaller {

    // Async Tasks and classes that refer to the model will refer to this event
    public abstract void syncAction(Intent intent);
}
