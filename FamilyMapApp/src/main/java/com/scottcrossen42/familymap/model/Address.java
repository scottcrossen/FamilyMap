package com.scottcrossen42.familymap.model;

import java.io.Serializable;

/**
 * Created by rodham on 2/29/2016.
 */
public class Address implements Serializable {

    private String street;
    private String city;
    private String state;
    private String zipCode;

    public Address() {
        setStreet("");
        setCity("");
        setState("");
        setZipCode("");
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s, %s %s", street, city, state, zipCode);
    }
}
