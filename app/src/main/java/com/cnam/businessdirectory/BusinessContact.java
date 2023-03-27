package com.cnam.businessdirectory;

import java.io.Serializable;

public class BusinessContact implements Serializable {

    private int contactId;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

    public BusinessContact(int contactId) {
        this.contactId = contactId;
    }

    public BusinessContact() {
    }

    public BusinessContact(int contactId, String firstName, String lastName, String address, String phoneNumber) {
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getContactId() {
        return contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
