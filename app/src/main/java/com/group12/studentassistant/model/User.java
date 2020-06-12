package com.group12.studentassistant.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User implements Comparable {

    private String firstName;

    private String lastName;

    private String email;

    private Profile profile;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // toString method
    @Override
    public String toString() {
        return "First Name : " + firstName + " Last Name : " + lastName + "\n e-Mail : " + email;
    }

    // CompareTo method compares profiles with respect to name than checks surname and mail
    @Override
    public int compareTo(Object o) {
        User other = (User) o;
        if (this.firstName.compareTo(other.firstName) == 0) {
            if (this.lastName.equals(other.lastName))
                if (this.email.equals(other.email))
                    return 0;
        } else if (this.firstName.compareTo(other.firstName) < 0) {
            return -1;
        }
        return 1;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("email", email);
        result.put("profile", profile);

        return result;
    }
}