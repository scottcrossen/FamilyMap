package com.scottcrossen42.familymap.ui.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.scottcrossen42.familymap.httpaccess.ServerSession;

/**
 * @author Scott Leland Crossen
 * @link http://scottcrossen42.com
 * Created on 12/1/16.
 */
public class MapFragment extends Fragment {
    private ServerSession session;
    public MapFragment() {
        session = ServerSession.getInstance();
    }
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
