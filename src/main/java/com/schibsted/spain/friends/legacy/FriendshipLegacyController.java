package com.schibsted.spain.friends.legacy;

import com.schibsted.spain.friends.legacy.context.SessionContext;
import com.schibsted.spain.friends.legacy.exception.SignupLegacyException;
import com.schibsted.spain.friends.legacy.model.User;
import com.schibsted.spain.friends.legacy.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/friendship")
public class FriendshipLegacyController {

  public static final String OK = "OK";
  private final SessionContext sessionContext;
  private final FriendshipService friendshipService;

  @Autowired
  public FriendshipLegacyController(SessionContext sessionContext,FriendshipService friendshipService) {
    this.sessionContext = sessionContext;
    this.friendshipService = friendshipService;
  }

  @PostMapping("/request")
  public ResponseEntity<String> requestFriendship(@RequestParam("usernameFrom") String usernameFrom, @RequestParam("usernameTo") String usernameTo, @RequestHeader("X-Password") String password) {
    final User userFrom = sessionContext.getUser(usernameFrom, password).orElseThrow(() -> new SignupLegacyException("No session"));
    final User userTo = sessionContext.getUser(usernameTo).orElseThrow(() -> new SignupLegacyException("No user registered"));
    if(userFrom.getUser().equals(userTo.getUser())){
      throw new SignupLegacyException("User cannot request friendship to himself");
    }
    friendshipService.pushFriendship(userFrom, userTo);
    return ResponseEntity.ok(OK);
  }

  @PostMapping("/accept")
  public ResponseEntity<String> acceptFriendship(@RequestParam("usernameFrom") String usernameFrom, @RequestParam("usernameTo") String usernameTo, @RequestHeader("X-Password") String password) {
    final User userFrom = sessionContext.getUser(usernameFrom, password).orElseThrow(() -> new SignupLegacyException("No session"));
    friendshipService.acceptRequestFriend(userFrom);
    return ResponseEntity.ok(OK);
  }

  @PostMapping("/decline")
  public ResponseEntity<String> declineFriendship(@RequestParam("usernameFrom") String usernameFrom,@RequestParam("usernameTo") String usernameTo, @RequestHeader("X-Password") String password) {
    final User userFrom = sessionContext.getUser(usernameFrom, password).orElseThrow(() -> new SignupLegacyException("No session"));
    friendshipService.declineRequestFriend(userFrom);
    return ResponseEntity.ok(OK);
  }

  @GetMapping("/list")
  public ResponseEntity<List<User>> listFriends(@RequestParam("username") String username, @RequestHeader("X-Password") String password) {
    final User userFrom = sessionContext.getUser(username, password).orElseThrow(() -> new SignupLegacyException("No session"));
    return ResponseEntity.ok(friendshipService.getFriends(userFrom));
  }
}
