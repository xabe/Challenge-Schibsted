package com.schibsted.spain.friends.legacy.context;

import com.schibsted.spain.friends.legacy.exception.SignupLegacyException;
import com.schibsted.spain.friends.legacy.model.User;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class SessionContextImplTest {

    private SessionContext sessionContext;

    @Before
    public void setup(){
        this.sessionContext = new SessionContextImpl();
    }

    @Test
    public void givenAUserWhenInvokePushUser() throws Exception {
        //Given
        final User user = new User("u", "p");

        //When
        sessionContext.pushUser(user);

        //Then
        MatcherAssert.assertThat(sessionContext.getUser("u").get(), Matchers.is(Matchers.notNullValue()));
    }

    @Test
    public void givenAUserNotSessionWhenInvokegetUserThenReturnException() throws Exception {
        //Given
        final User user = new User("u", "p");

        //When
        sessionContext.pushUser(user);
        final Optional<User> result = sessionContext.getUser("x", "x");

        //Then
        MatcherAssert.assertThat(result, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(result.isPresent(), Matchers.is(false));
    }

    @Test
    public void givenAUserAndPasswordIncorrectWhenInvokegetUserThenReturnException() throws Exception {
        //Given
        final User user = new User("u", "p");

        //When
        sessionContext.pushUser(user);
        final Optional<User> result = sessionContext.getUser("u", "x");

        //Then
        MatcherAssert.assertThat(result, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(result.isPresent(), Matchers.is(false));
    }

    @Test
    public void givenAUserAndPasswordWhenInvokegetUserThenReturnUser() throws Exception {
        //Given
        final User user = new User("u", "p");

        //When
        sessionContext.pushUser(user);
        final Optional<User> result = sessionContext.getUser("u", "p");

        //Then
        MatcherAssert.assertThat(result, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(result.isPresent(), Matchers.is(true));
    }

    @Test(expected = SignupLegacyException.class)
    public void givenATwoUserWhenInvokePushUserThrowException() throws Exception {
        //Given
        final User user = new User("u", "p");

        //When
        sessionContext.pushUser(user);
        sessionContext.pushUser(user);

    }
}