package com.scottcrossen42.familymap.model;

import android.content.Intent;
import android.util.Log;

import com.scottcrossen42.familymap.Constants;
import com.scottcrossen42.familymap.httpaccess.GetRequest;
import com.scottcrossen42.familymap.httpaccess.HTTPGetter;
import com.scottcrossen42.familymap.ui.activities.ITaskCaller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Scott Leland Crossen
 * @link http://scottcrossen42.com
 * Created on 12/1/16.
 */
public class Model implements HTTPGetter {
    private static Model instance = new Model();
    private Map<String, Person> people = new HashMap();
    private Map<String, Event> events = new HashMap();
    private Map<String, List<String>> person_events = new HashMap();
    private ITaskCaller calling_object;
    private boolean persons_received;
    public static Model getInstance() { return instance; }

    public void addPerson(Person person) { people.put(person.getID(), person); }
    public void addEvent(Event event) {
        String person_id = event.getPersonID();
        if (people.containsKey(person_id))
            event.setPersonName(people.get(person_id).getFullName()); 
        events.put(event.getID(), event);
        if (!person_events.containsKey(event.getPersonID()))
            person_events.put(event.getPersonID(), new LinkedList<String>());
        person_events.get(event.getPersonID()).add(event.getID());
    }
    public boolean isEvent(String id) { return events.containsKey(id); }
    public Person getPerson(String id) { return people.get(id); }
    public Event getEvent(String id) { return events.get(id); }
    public Collection<Person> getPeople() { return people.values(); }
    public Collection<Event> getEvents() { return events.values(); }
    public Collection<Event> getFilteredPersonEvents(String person_id) { return Filter.getInstance().filterEvents(getPersonEvents(person_id)); }
    public Collection<Event> getPersonEvents(String person_id) {
        if (person_events.get(person_id) != null) {
            Set<Event> to_return = new TreeSet<>();
            Iterator<String> index = person_events.get(person_id).iterator(); //fill list with events
            while (index.hasNext()) to_return.add(events.get(index.next())); 
            return to_return;
        }
        else return null;
    }
    public Event getFilteredEarliestEvent(String person_id) {
        Collection<Event> person_events = getFilteredPersonEvents(person_id);
        return getEarliestEvent(person_events);
    }
    public Event getEarliestEvent(String person_id) {
        Collection<Event> person_events = getPersonEvents(person_id);
        return getEarliestEvent(person_events);
    }
    private Event getEarliestEvent(Collection<Event> person_events) {
        if (person_events != null) {
            if (person_events.size() > 0)
                return person_events.iterator().next();
            else return null; 
        }
        else
            return null;
    }
    public Event getSpouseEvent(String person_id) {
        String spouse_id = people.get(person_id).getSpouse();
        return getFilteredEarliestEvent(spouse_id);
    }




    private void clear() {
        people = new HashMap();
        events = new HashMap();
        person_events = new HashMap();
        persons_received = false;
    }
    public void syncData(ITaskCaller caller) {
        clear();
        calling_object = caller;
        persons_received = false;
        GetRequest persons = new GetRequest(this, "/person/");
        persons.execute();
    }




    private void importDataPiece(JSONObject to_import) throws org.json.JSONException {
        if (to_import.has("eventID")) importEvent(to_import);
        else importPerson(to_import);
    }
    private void importEvent(JSONObject to_import) throws org.json.JSONException {
        Event event = new Event(
                to_import.getString("eventID"),
                to_import.getString("personID"),
                to_import.getDouble("latitude"),
                to_import.getDouble("longitude"),
                to_import.getString("country"),
                to_import.getString("city"),
                to_import.getString("description"),
                to_import.getInt("year"));
        addEvent(event);
    }
    private void importPerson(JSONObject to_import) throws org.json.JSONException {
        Person person = new Person(
                to_import.getString("personID"),
                to_import.getString("firstName"),
                to_import.getString("lastName"),
                to_import.getString("gender"));
        if (to_import.has("spouse")) person.setSpouse(to_import.getString("spouse")); 
        if (to_import.has("father")) person.setFather(to_import.getString("father"));
        if (to_import.has("mother")) person.setMother(to_import.getString("mother"));
        addPerson(person);
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
                GetRequest events = new GetRequest(this, "/event/");
                events.execute();
            }
            else {
                Filter.getInstance().enumerateFilters();
                if (calling_object != null) {
                    Intent intent = new Intent();
                    intent.setAction("sync done");
                    calling_object.syncAction(intent);
                }
            }
        } catch(org.json.JSONException e) {
            Log.e(Constants.TAG, "Corrupt data received");
            HTTPError(e);
        }
    }
    @Override
    public void HTTPError(Exception error) {
        if (calling_object != null) {
            Intent intent = new Intent();
            intent.setAction("http error");
            intent.putExtra("error", error.getMessage());
            calling_object.syncAction(intent);
        }
    }
}
