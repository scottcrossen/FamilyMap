package com.scottcrossen42.familymap.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.scottcrossen42.familymap.R;

public class LoginFragment extends android.support.v4.app.Fragment {


    private EditText nameEditText;
    private EditText passwordEditText;
    private EditText hostEditText;
    private EditText portEditText;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        nameEditText = (EditText)v.findViewById(R.id.nameEditText);
        passwordEditText = (EditText)v.findViewById(R.id.passwordEditText);
        hostEditText = (EditText)v.findViewById(R.id.hostEditText);
        portEditText = (EditText)v.findViewById(R.id.portEditText);
        return v;
    }
}
