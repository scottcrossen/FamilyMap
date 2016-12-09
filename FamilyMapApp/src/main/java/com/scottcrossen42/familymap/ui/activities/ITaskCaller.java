package com.scottcrossen42.familymap.ui.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * @author Scott Leland Crossen
 * @link http://scottcrossen42.com
 * Created on 12/1/16.
 */
public interface ITaskCaller {

    public abstract void syncAction(Intent intent);
}
