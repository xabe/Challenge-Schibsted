package com.schibsted.spain.friends.legacy.service;

import com.schibsted.spain.friends.legacy.model.User;

import java.util.List;

public interface FriendshipService {

    void pushFriendship(User userFrom, User userTo);

    List<Friendship> getFriendships();

    void acceptRequestFriend(User user);

    void declineRequestFriend(User user);

    List<User> getFriends(User user);
}
