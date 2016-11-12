package com.scottcrossen42.familymap.model;

import java.io.Serializable;

/**
 * Created by rodham on 2/29/2016.
 */
public class Order implements Serializable {

    private Address shippingAddress;
    private Address billingAddress;

    public Order() {
        setShippingAddress(null);
        setBillingAddress(null);
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    @Override
    public String toString() {
        return String.format("SHIPPING ADDRESS: %s\nBILLING ADDRESS: %s",
                shippingAddress,
                (billingAddress != null) ? billingAddress : shippingAddress);
    }
}
