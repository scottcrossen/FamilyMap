package com.scottcrossen42.familymap.model;

import android.util.Log;

import com.scottcrossen42.familymap.Constants;
import com.scottcrossen42.familymap.httpaccess.HTTPGetter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by slxn42 on 12/1/16.
 */
public class Model implements HTTPGetter {
    private static Model instance = new Model();
    private Map<String, Person> people = new HashMap();
    private Map<String, Event> events = new HashMap();
    private Map<String, List<String>> person_events = new HashMap();
    private SyncActivity sync_caller;
    private boolean persons_received;

    public static Model getInstance() {
        return instance;
    }
    public void addPerson(Person person) {
        people.put(person.getID(), person);
    }

    @Override
    public void rxData(String result) {
        try {
            JSONObject json_obj = new JSONObject(result);
            JSONArray data = json_obj.getJSONArray("data");
            for(int i=0; i < data.length(); i++) {
                importDataPiece(data.getJSONObject(i));
            }
            if (!persons_received) {
                persons_received = true;
                //now that the persons are recieved, go get the events
                GetTask events = new GetTask(this, "/event/");
                events.execute();
            }
            else {
                Filter.getInstance().enumerateFilters();
                sync_caller.SyncDone();
            }

        } catch(org.json.JSONException e) {
            Log.e(Constants.TAG, "Corrupt data received");
            HTTPError(e);
        }

    }

    @Override
    public void HTTPError(Exception error) {

    }
}
