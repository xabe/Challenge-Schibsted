package com.schibsted.spain.friends.legacy;

import com.schibsted.spain.friends.legacy.context.SessionContext;
import com.schibsted.spain.friends.legacy.exception.SignupLegacyException;
import com.schibsted.spain.friends.legacy.model.User;
import com.schibsted.spain.friends.legacy.validate.ValidateUser;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class SignupLegacyControllerTest {

    private SessionContext sessionContext;
    private ValidateUser validateUser;
    private SignupLegacyController signupLegacyController;


    @Before
    public void setup(){
        this.sessionContext = mock(SessionContext.class);
        this.validateUser = mock(ValidateUser.class);
        this.signupLegacyController = new SignupLegacyController(validateUser, sessionContext);
    }

    @Test(expected = SignupLegacyException.class)
    public void givenAUsernameAndPasswordIncorrectWhenInvokeSignUpThenReturnResponseError() throws Exception {
        //Given
        final String username = "user";
        final String password = "pass";
        when(validateUser.validateUser(any(),any())).thenReturn(Optional.empty());

        //When
        signupLegacyController.signUp(username, password);

    }

    @Test
    public void givenAUsernameAndPasswordCorrectWhenInvokeSignUpThenReturnResponseError() throws Exception {
        //Given
        final String username = "user";
        final String password = "pass";
        when(validateUser.validateUser(any(),any())).thenReturn(Optional.of(new User("user","pass")));

        //When
        final ResponseEntity<String> result = signupLegacyController.signUp(username, password);

        //Then
        MatcherAssert.assertThat(result, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(result.getBody(), Matchers.is("OK"));
        verify(sessionContext).pushUser(any());
    }

}