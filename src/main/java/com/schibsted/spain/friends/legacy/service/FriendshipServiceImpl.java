package com.schibsted.spain.friends.legacy.service;

import com.schibsted.spain.friends.legacy.exception.SignupLegacyException;
import com.schibsted.spain.friends.legacy.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

@Component
public class FriendshipServiceImpl implements FriendshipService {

    private final List<Friendship> friendships;
    private final Map<String, List<User>> friendsMap;

    public FriendshipServiceImpl() {
        this.friendships = Collections.synchronizedList(new ArrayList());
        this.friendsMap = new ConcurrentHashMap<>();
    }

    @Override
    public void pushFriendship(User userFrom, User userTo) {
        final Friendship friendship = new Friendship(userFrom, userTo);
        if (friendships.contains(friendship)) {
            throw new SignupLegacyException("user that already has a pending request from ");
        }
        this.friendships.add(friendship);
    }

    @Override
    public List<Friendship> getFriendships() {
        return this.friendships;
    }

    @Override
    public void acceptRequestFriend(User user) {
        final Friendship friendship = this.friendships.stream().
                filter(getRequestFriend(user)).
                filter(this::pendingAcceptRequest).
                findFirst()
                .orElseThrow(() -> new SignupLegacyException("Not have request pending accept"));
        addFriendsMap(friendship.getUserTo().getUser(), friendship.getUserFrom());
        addFriendsMap(friendship.getUserFrom().getUser(), friendship.getUserTo());
    }

    private void addFriendsMap(String user, User friend) {
        friendsMap.computeIfAbsent(user, key -> new ArrayList<>());
        friendsMap.computeIfPresent(user, (key, list) -> {
            list.add(friend);
            return list;
        });
    }

    private boolean pendingAcceptRequest(Friendship friendship) {
        if (!friendship.isAccept()) {
            friendship.setAccept(true);
            return true;
        }
        return false;
    }

    private Predicate<Friendship> getRequestFriend(User user) {
        return friendship -> friendship.getUserTo().getUser().equals(user.getUser());
    }

    @Override
    public void declineRequestFriend(User user) {
        if (!this.friendships.removeIf(declineRequest(user))) {
            throw new SignupLegacyException("Not have request for decline");
        }
    }

    private Predicate<Friendship> declineRequest(User userFrom) {
        return friendship -> friendship.getUserTo().getUser().equals(userFrom.getUser());
    }

    @Override
    public List<User> getFriends(User user) {
        return friendsMap.getOrDefault(user.getUser(), Collections.emptyList());
    }

}
