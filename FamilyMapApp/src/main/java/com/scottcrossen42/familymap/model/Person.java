package com.scottcrossen42.familymap.model;

/**
 * @author Scott Leland Crossen
 * @link http://scottcrossen42.com
 * Created on 12/1/16.
 */
public class Person {
    private String id;
    private String first_name;
    private String last_name;
    private String gender;
    /* These next fields will be populated second */
    private String spouse;
    private String father;
    private String mother;

    public Person(String personID, String firstName, String lastName, String gender) {
        id = personID;
        first_name = firstName;
        last_name = lastName;
        this.gender = gender;
    }

    public String getGender() {
        if (gender.equals("f")) return "Female";
        else if (gender.equals("m")) return "Male";
        else return gender;
    }
    public void setSpouse(String spouse) { this.spouse = spouse; }
    public void setFather(String father) { this.father = father; }
    public void setMother(String mother) { this.mother = mother; }
    public String getFirstName() { return first_name; }
    public String getLastName() { return last_name; }
    public String getFullName() { return getFirstName() + " " + getLastName(); }
    public String getSpouse() { return spouse; }
    public String getID() { return id; }
    public String getFather() { return father; }
    public String getMother() { return mother; }
    public boolean hasFather() { return father != null; }
    public boolean hasMother() { return mother != null; }
}
