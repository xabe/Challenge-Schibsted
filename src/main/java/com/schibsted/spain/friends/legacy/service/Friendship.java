package com.schibsted.spain.friends.legacy.service;

import com.schibsted.spain.friends.legacy.model.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Friendship {
    private final User userFrom;
    private final User userTo;
    private boolean accept;

    public Friendship(User userFrom, User userTo) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.accept = false;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Friendship) {
            Friendship that = (Friendship) o;

            return new EqualsBuilder()
                    .append(userFrom.getUser(), that.userFrom.getUser())
                    .append(userTo.getUser(), that.userTo.getUser())
                    .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userFrom.getUser())
                .append(userTo.getUser())
                .toHashCode();
    }

}
