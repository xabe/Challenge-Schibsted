package com.schibsted.spain.friends.legacy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class User {

    private String user;
    private String pass;

    public User(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    @JsonIgnore
    public String getPass() {
        return pass;
    }

    public String getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User user1 = (User) o;

            return new EqualsBuilder()
                    .append(user, user1.user)
                    .append(pass, user1.pass)
                    .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(user)
                .append(pass)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "user='" + user + '\''+
                '}';
    }
}
