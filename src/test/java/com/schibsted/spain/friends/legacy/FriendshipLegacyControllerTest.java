package com.schibsted.spain.friends.legacy;

import com.schibsted.spain.friends.legacy.context.SessionContext;
import com.schibsted.spain.friends.legacy.exception.FriendShipException;
import com.schibsted.spain.friends.legacy.model.User;
import com.schibsted.spain.friends.legacy.service.FriendshipService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FriendshipLegacyControllerTest {

    private SessionContext sessionContext;
    private FriendshipService friendshipService;
    private FriendshipLegacyController friendshipLegacyController;

    @Before
    public void setup(){
        this.sessionContext = mock(SessionContext.class);
        this.friendshipService = mock(FriendshipService.class);
        this.friendshipLegacyController = new FriendshipLegacyController(sessionContext,friendshipService);
    }

    @Test(expected = FriendShipException.class)
    public void givenAUsernameFromNotSessionWhenInvokeRequestFriendshipThenReturnSignupLegacyException() throws Exception {
        //Given
        final String usernameFrom = "a";
        final String usernameTo = "b";
        final String password = "x";
        when(sessionContext.getUser(any(),any())).thenReturn(Optional.empty());

        //When
        this.friendshipLegacyController.requestFriendship(usernameFrom, usernameTo, password);

        //Then
    }

    @Test(expected = FriendShipException.class)
    public void givenAUsernameToNotRegisteredWhenInvokeRequestFriendshipThenReturnSignupLegacyException() throws Exception {
        //Given
        final String usernameFrom = "a";
        final String usernameTo = "b";
        final String password = "x";
        when(sessionContext.getUser(any(),any())).thenReturn(Optional.of(new User("x","x")));
        when(sessionContext.getUser(any())).thenReturn(Optional.empty());

        //When
        this.friendshipLegacyController.requestFriendship(usernameFrom, usernameTo, password);

        //Then
    }

    @Test(expected = FriendShipException.class)
    public void givenAUsernameFromAndUserNameToEqualsWhenInvokeRequestFriendshipThenReturnSignupLegacyException() throws Exception {
        //Given
        final String usernameFrom = "a";
        final String usernameTo = "b";
        final String password = "x";
        when(sessionContext.getUser(any(),any())).thenReturn(Optional.of(new User("x","x")));
        when(sessionContext.getUser(any())).thenReturn(Optional.of(new User("x","x")));

        //When
        this.friendshipLegacyController.requestFriendship(usernameFrom, usernameTo, password);

        //Then
    }

    @Test
    public void givenAUsernameFromAndUserNameWhenInvokeRequestFriendshipThenReturnResponseEntity() throws Exception {
        //Given
        final String usernameFrom = "a";
        final String usernameTo = "b";
        final String password = "x";
        when(sessionContext.getUser(any(),any())).thenReturn(Optional.of(new User("x","x")));
        when(sessionContext.getUser(any())).thenReturn(Optional.of(new User("y","x")));

        //When
        final ResponseEntity<String> result = this.friendshipLegacyController.requestFriendship(usernameFrom, usernameTo, password);

        //Then
        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is("OK"));
    }

    @Test(expected = FriendShipException.class)
    public void givenAUsernameFromAndUserNameWhenInvokeAcceptFriendshipThenReturnException() throws Exception {
        //Given
        final String usernameFrom = "a";
        final String usernameTo = "b";
        final String password = "x";
        when(sessionContext.getUser(any(),any())).thenReturn(Optional.empty());

        //When
        this.friendshipLegacyController.acceptFriendship(usernameFrom, usernameTo, password);
    }

    @Test
    public void givenAUsernameFromAndUserNameWhenInvokeAcceptFriendshipThenReturnResponseEntity() throws Exception {
        //Given
        final String usernameFrom = "a";
        final String usernameTo = "b";
        final String password = "x";
        when(sessionContext.getUser(any(),any())).thenReturn(Optional.of(new User("x","x")));

        //When
        final ResponseEntity<String> result = this.friendshipLegacyController.acceptFriendship(usernameFrom, usernameTo, password);

        //Then
        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is("OK"));
        verify(friendshipService).acceptRequestFriend(any());
    }



    @Test(expected = FriendShipException.class)
    public void givenAUsernameFromAndUserNameWhenInvokeDeclineFriendshipThenReturnException() throws Exception {
        //Given
        final String usernameFrom = "a";
        final String usernameTo = "b";
        final String password = "x";
        when(sessionContext.getUser(any(),any())).thenReturn(Optional.empty());

        //When
        this.friendshipLegacyController.declineFriendship(usernameFrom, usernameTo, password);
    }

    @Test
    public void givenAUsernameFromAndUserNameWhenInvokeDeclineFriendshipThenReturnResponseEntity() throws Exception {
        //Given
        final String usernameFrom = "a";
        final String usernameTo = "b";
        final String password = "x";
        when(sessionContext.getUser(any(),any())).thenReturn(Optional.of(new User("x","x")));

        //When
        final ResponseEntity<String> result = this.friendshipLegacyController.declineFriendship(usernameFrom, usernameTo, password);

        //Then
        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is("OK"));
        verify(friendshipService).declineRequestFriend(any());
    }

    @Test(expected = FriendShipException.class)
    public void givenAUsernameAndPasswordWhenInvokeGetFriendshipThenReturnException() throws Exception {
        //Given
        final String username = "a";
        final String password = "x";
        when(sessionContext.getUser(any(),any())).thenReturn(Optional.empty());

        //When
        this.friendshipLegacyController.listFriends(username, password);
    }

    @Test
    public void givenAUsernameAndPasswordWhenInvokeGetFriendshipThenReturnResponseEntity() throws Exception {
        //Given
        final String username = "a";
        final String password = "x";
        when(sessionContext.getUser(any(),any())).thenReturn(Optional.of(new User("x","x")));
        when(friendshipService.getFriends(any())).thenReturn(Arrays.asList(new User("b","s")));

        //When
        final ResponseEntity<List<User>> result = this.friendshipLegacyController.listFriends(username, password);

        //Then
        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is(notNullValue()));
    }

}