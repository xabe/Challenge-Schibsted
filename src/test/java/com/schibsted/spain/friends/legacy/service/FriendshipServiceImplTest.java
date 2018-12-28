package com.schibsted.spain.friends.legacy.service;

import com.schibsted.spain.friends.legacy.exception.SignupLegacyException;
import com.schibsted.spain.friends.legacy.model.User;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class FriendshipServiceImplTest {

    private FriendshipService friendshipService;

    @Before
    public void setup() {
        this.friendshipService = new FriendshipServiceImpl();
    }

    @Test
    public void givenAUserFromAndUserToWhenInvokePushFriendshipAddRequest() throws Exception {
        //Given
        final User userFrom = new User("x","y");
        final User userTo = new User("d","y");

        //When
        this.friendshipService.pushFriendship(userFrom,userTo);

        //Then
        MatcherAssert.assertThat(this.friendshipService.getFriendships(), Matchers.is(Matchers.hasSize(1)));
    }

    @Test(expected = SignupLegacyException.class)
    public void givenAUserFromAndUserToWhenInvokePushFriendshipThrowException() throws Exception {
        //Given
        final User userFrom = new User("x","y");
        final User userTo = new User("d","y");

        //When
        this.friendshipService.pushFriendship(userFrom,userTo);
        this.friendshipService.pushFriendship(userFrom,userTo);
    }

    @Test
    public void givenAUserFromWhenInvokeAcceptRequestFriendDoNothing() throws Exception {
        //Given
        final User userFrom = new User("x","y");
        final User userTo = new User("d","y");

        //When
        this.friendshipService.pushFriendship(userFrom,userTo);


        this.friendshipService.acceptRequestFriend(userTo);

        //Then
        MatcherAssert.assertThat(this.friendshipService.getFriendships().get(0).isAccept(), Matchers.is(true));
    }


    @Test(expected = SignupLegacyException.class)
    public void givenAUserFromWhenInvokeAcceptRequestFriendThrowException() throws Exception {
        //Given
        final User userFrom = new User("x","y");
        final User userTo = new User("d","y");

        //When
        this.friendshipService.pushFriendship(userFrom,userTo);


        this.friendshipService.acceptRequestFriend(userTo);
        this.friendshipService.acceptRequestFriend(userTo);
    }


    @Test
    public void givenAUserFromWhenInvokeDeclineRequestFriendDoNothing() throws Exception {
        //Given
        final User userFrom = new User("x","y");
        final User userTo = new User("d","y");

        //When
        this.friendshipService.pushFriendship(userFrom,userTo);


        this.friendshipService.declineRequestFriend(userTo);

        //Then
        MatcherAssert.assertThat(this.friendshipService.getFriendships().isEmpty(), Matchers.is(true));
    }


    @Test(expected = SignupLegacyException.class)
    public void givenAUserFromWhenInvokeDeclineRequestFriendThrowException() throws Exception {
        //Given
        final User userFrom = new User("x","y");
        final User userTo = new User("d","y");

        //When
        this.friendshipService.pushFriendship(userFrom,userTo);


        this.friendshipService.declineRequestFriend(userTo);
        this.friendshipService.declineRequestFriend(userTo);
    }

    @Test
    public void givenAUserWhenInvokeGetFriendsThenReturnList() throws Exception {
        //Given
        final User userFrom = new User("x","y");
        final User userTo = new User("d","y");
        this.friendshipService.pushFriendship(userFrom,userTo);
        this.friendshipService.acceptRequestFriend(userTo);

        //When
        final List<User> resultFrom = this.friendshipService.getFriends(userFrom);
        final List<User> resultTo = this.friendshipService.getFriends(userTo);

        //Then
        MatcherAssert.assertThat(resultFrom, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(resultFrom, Matchers.is(Matchers.hasSize(1)));

        MatcherAssert.assertThat(resultTo, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(resultTo, Matchers.is(Matchers.hasSize(1)));


    }


}