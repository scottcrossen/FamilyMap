package com.scottcrossen42.familymap.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.scottcrossen42.familymap.Constants;
import com.scottcrossen42.familymap.R;
import com.scottcrossen42.familymap.httpaccess.GetRequest;
import com.scottcrossen42.familymap.httpaccess.HTTPGetter;
import com.scottcrossen42.familymap.httpaccess.HTTPPoster;
import com.scottcrossen42.familymap.httpaccess.PostRequest;
import com.scottcrossen42.familymap.httpaccess.ServerSession;
import com.scottcrossen42.familymap.ui.activities.IFragmentCaller;

import org.json.JSONObject;

public class LoginFragment extends Fragment implements HTTPPoster, HTTPGetter {
    private EditText nameEditText;
    private EditText passwordEditText;
    private EditText hostEditText;
    private EditText portEditText;
    private Button login_button;
    private Context context;
    private ServerSession session;
    private IFragmentCaller calling_object;
    public LoginFragment() {
        session = ServerSession.getInstance();
    }
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public void setParent(IFragmentCaller _calling_object){
        calling_object=_calling_object;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        login_button = (Button)v.findViewById(R.id.login_button);
        nameEditText = (EditText)v.findViewById(R.id.nameEditText);
        passwordEditText = (EditText)v.findViewById(R.id.passwordEditText);
        hostEditText = (EditText)v.findViewById(R.id.hostEditText);
        portEditText = (EditText)v.findViewById(R.id.portEditText);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClicked();
            }
        });
        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    private void onLoginClicked() {
        session.setUserName(nameEditText.getText().toString());
        session.setHost(hostEditText.getText().toString());
        session.setPort(portEditText.getText().toString());
        String post_data =
            "{\n" +
                "username:" + nameEditText.getText().toString() + ",\n" +
                "password:" + passwordEditText.getText().toString() + "\n" +
            "}";
        PostRequest task = new PostRequest(this, "/user/login", post_data);
        task.execute();
    }
    @Override
    public void HTTPError(Exception error) {
        Log.e(Constants.TAG, "Unable to login. " + error.getMessage());
        Toast.makeText(context, "ERROR: unable to login.", Toast.LENGTH_LONG).show();
        if (calling_object != null) {
            Intent intent = new Intent();
            intent.setAction("login failed");
            calling_object.fragmentAction(this, intent);
        }
    }
    @Override
    public void txData(String result) {
        try {
            JSONObject json_obj = new JSONObject(result);
            String person_id = json_obj.getString("personId");
            session.setAuth(json_obj.getString("Authorization"));
            //Filter.getInstance().setUser(person_id);
            GetRequest task = new GetRequest(this, "/person/" + person_id);
            task.execute();
        }
        catch(org.json.JSONException e) {
            Log.e(Constants.TAG, "Corrupt data received: " + result);
            HTTPError(e);
        }
    }
    @Override
    public void rxData(String result) {
        try {
            JSONObject json_obj = new JSONObject(result);
            Toast.makeText(context, "welcome " + json_obj.getString("firstName") + " " + json_obj.getString("lastName"), Toast.LENGTH_LONG).show();
            if (calling_object != null) {
                Intent intent = new Intent();
                intent.setAction("fragment finished");
                calling_object.fragmentAction(this, intent);
            }
        } catch(org.json.JSONException e) {
            Log.e(Constants.TAG, "Corrupt data received: " + result);
            HTTPError(e);
        }
    }
}
