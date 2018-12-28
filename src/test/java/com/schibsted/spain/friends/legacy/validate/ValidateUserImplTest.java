package com.schibsted.spain.friends.legacy.validate;

import com.schibsted.spain.friends.legacy.model.User;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class ValidateUserImplTest {

    private ValidateUser validateUser;

    @Before
    public void setup(){
        this.validateUser = new ValidateUserImpl();
    }

    @Test
    public void givenAUsernameAndPasswordNullWhenInvokeValidateThenReturnOptionalEmpty() throws Exception {
        //Given
        final String username = null;
        final String password = null;

        //When
        final Optional<User> result = validateUser.validateUser(username, password);

        //Then
        MatcherAssert.assertThat(result, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(result.isPresent(), Matchers.is(false));
    }

    @Test
    public void givenAUsernameIncorrectLengthWhenInvokeValidateThenReturnOptionalEmpty() throws Exception {
        //Given
        final String username = "a";
        final String password = "abcd1234";

        //When
        final Optional<User> result = validateUser.validateUser(username, password);

        //Then
        MatcherAssert.assertThat(result, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(result.isPresent(), Matchers.is(false));
    }

    @Test
    public void givenAUsernameIncorrectCharacterWhenInvokeValidateThenReturnOptionalEmpty() throws Exception {
        //Given
        final String username = "abcd1-";
        final String password = "abcd1234";

        //When
        final Optional<User> result = validateUser.validateUser(username, password);

        //Then
        MatcherAssert.assertThat(result, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(result.isPresent(), Matchers.is(false));
    }

    @Test
    public void givenAPassowrdIncorrectLengthWhenInvokeValidateThenReturnOptionalEmpty() throws Exception {
        //Given
        final String username = "abcd1234";
        final String password = "a";

        //When
        final Optional<User> result = validateUser.validateUser(username, password);

        //Then
        MatcherAssert.assertThat(result, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(result.isPresent(), Matchers.is(false));
    }

    @Test
    public void givenAPasswordIncorrectCharacterWhenInvokeValidateThenReturnOptionalEmpty() throws Exception {
        //Given
        final String username = "abcd1234";
        final String password = "abcd1234-";

        //When
        final Optional<User> result = validateUser.validateUser(username, password);

        //Then
        MatcherAssert.assertThat(result, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(result.isPresent(), Matchers.is(false));
    }


    @Test
    public void givenAUsernameAndPasswordCorrectWhenInvokeValidateThenReturnOptionalEmpty() throws Exception {
        //Given
        final String username = "abc12";
        final String password = "abcd1234";

        //When
        final Optional<User> result = validateUser.validateUser(username, password);

        //Then
        MatcherAssert.assertThat(result, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(result.isPresent(), Matchers.is(true));
        MatcherAssert.assertThat(result.get().getUser(), Matchers.is(username));
        MatcherAssert.assertThat(result.get().getPass(), Matchers.is(password));
    }

}