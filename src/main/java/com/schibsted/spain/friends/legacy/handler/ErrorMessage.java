package com.schibsted.spain.friends.legacy.handler;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ErrorMessage {

    private final List<String> errors;

    private final OffsetDateTime time;

    public ErrorMessage(List<String> errors) {
        this.errors = errors;
        this.time = OffsetDateTime.now();
    }

    public ErrorMessage(String error) {
        this(Collections.singletonList(error));
    }

    public ErrorMessage(String... errors) {
        this(Arrays.asList(errors));
    }

    public List<String> getErrors() {
        return errors;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ErrorMessage) {
            ErrorMessage that = (ErrorMessage) o;

            return new EqualsBuilder()
                    .append(errors, that.errors)
                    .append(time, that.time)
                    .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(errors)
                .append(time)
                .toHashCode();
    }
}
