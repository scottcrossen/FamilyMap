package com.scottcrossen42.familymap.UI;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.scottcrossen42.familymap.R;
import com.scottcrossen42.familymap.model.ServerSession;

public class LoginFragment extends android.support.v4.app.Fragment{


    private EditText nameEditText;
    private EditText passwordEditText;
    private EditText hostEditText;
    private EditText portEditText;
    private Button login_button;
    private Context context;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

    private void onButtonClicked() {
        ServerSession info = new ServerSession();
        info.setUserName(nameEditText.getText().toString());
        info.setPassword(passwordEditText.getText().toString());
        info.setHost(hostEditText.getText().toString());
        info.setPort(portEditText.getText().toString());
    }
}
