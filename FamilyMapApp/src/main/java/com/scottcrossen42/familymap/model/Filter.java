package com.scottcrossen42.familymap.model;

import com.scottcrossen42.familymap.httpaccess.ServerSession;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Scott Leland Crossen
 * @link http://scottcrossen42.com
 * Created on 12/1/16.
 */
public class Filter {

    public enum Type {DESCRIPTION, MALE_GENDER, FEMALE_GENDER, FATHER_SIDE, MOTHER_SIDE}
    private static Filter ourInstance = new Filter();
    private String user;
    Model model = Model.getInstance();
    private boolean showFatherSide = false;
    private boolean showMotherSide = false;
    private boolean showMales = false;
    private boolean showFemales = false;
    boolean male_checked = true;
    boolean female_checked = true;
    boolean father_checked = true;
    boolean mother_checked = true;
    private Map<String, Boolean> is_checked;

    // The Login Fragment will set the user:
    private Filter() {/*setUser(ServerSession.getInstance().getUserName());*/}

    public static Filter getInstance() { return ourInstance; }

    // This will the main call to the class:
    public Collection<Event> filterEvents(Collection<Event> to_filter) {
        if (to_filter != null) {
            LinkedList<Event> to_return = new LinkedList<>();
            Iterator<Event> index = to_filter.iterator();
            // Loop through the active filter of events and filter:
            while (index.hasNext()) {
                Event current_event = index.next();
                if (filterEvent(current_event)) to_return.add(current_event);
            }
            return to_return;
        }
        else return null;
    }

    // This sets up the filters:
    public void enumerateFilters() {
        Model model = Model.getInstance();
        Iterator<Event> index = model.getEvents().iterator();
        is_checked = new TreeMap<>();
        // The default behavior:
        showMales = false;
        showFemales = false;
        showFatherSide = false;
        showMotherSide = false;
        // iterate through and check what static filters need to be shown:
        while(index.hasNext()) {
            Event event = index.next();
            if (!is_checked.containsKey(event.getDescription().toLowerCase()))
                is_checked.put(event.getDescription().toLowerCase(), true);
            Person event_person = model.getPerson(event.getPersonID());
            if (event_person.getGender().equals("Male")) showMales = true;
            if (event_person.getGender().equals("Female")) showFemales = true;
        }
        // The person is stored in the model:
        Person user_person = model.getPerson(user);
        if(user_person !=null) {
            if (user_person.hasFather()) showFatherSide = true; // Only show sides if person exists:
            if (user_person.hasMother()) showMotherSide = true;
        }
        // Sometimes the user isn't. We'll just create one instead of throwing an error.
        else{
            if (user==null)
                user_person=new Person(ServerSession.getInstance().getUserName(),"","","trans");
            else
                user_person=new Person(user,"","","trans");
            setUser(user_person.getID());
            model.addPerson(user_person);
        }
    }

    // Each event in the iteration goes through this:
    private boolean filterEvent(Event event) {
        if (!is_checked.get(event.getDescription().toLowerCase())) return false;
        else if (!father_checked && isDescendant(model.getPerson(user).getFather(), event.getPersonID())) return false;
        else if (!mother_checked && isDescendant(model.getPerson(user).getMother(), event.getPersonID())) return false;
        else if (!male_checked && model.getPerson(event.getPersonID()).getGender().equals("Male")) return false;
        else if (!female_checked && model.getPerson(event.getPersonID()).getGender().equals("Female")) return false;
        else return true;
    }

    // This is for the family-side filter:
    private boolean isDescendant(String current, String to_find) {
        if (current != null) {
            if (current.equals(to_find)) return true;
            else {
                if (isDescendant(model.getPerson(current).getFather(), to_find)) return true;
                else if (isDescendant(model.getPerson(current).getMother(), to_find)) return true;
                else return false;
            }
        }
        else return false;
    }

    // Which filters are active? is this one?
    public boolean isChecked(Type parent, String description) {
        switch (parent) {
            case DESCRIPTION:
                return is_checked.get(description);
            case MALE_GENDER:
                return male_checked;
            case FEMALE_GENDER:
                return female_checked;
            case FATHER_SIDE:
                return father_checked;
            case MOTHER_SIDE:
                return mother_checked;
            default:
                assert(false);
                return false;
        }
    }

    // Set the filters
    public void setChecked(Type parent, String id, boolean checked) {
        switch (parent) {
            case DESCRIPTION:
                if (is_checked.containsKey(id)) is_checked.remove(id);
                is_checked.put(id, checked);
                break;
            case MALE_GENDER:
                male_checked = checked;
                break;
            case FEMALE_GENDER:
                female_checked = checked;
                break;
            case FATHER_SIDE:
                father_checked = checked;
                break;
            case MOTHER_SIDE:
                mother_checked = checked;
                break;
            default:
                assert(false);
        }
    }

    // One-liners:
    public void setUser(String user) { this.user = user; }
    public String getUser() { return user; }
    public Set<String> getFilters() { return is_checked.keySet(); }
    public boolean showFatherSide() { return showFatherSide; }
    public boolean showMotherSide() { return showMotherSide; }
    public boolean showMales() { return showMales; }
    public boolean showFemales() { return showFemales; }
}
