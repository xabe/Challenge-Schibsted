package com.schibsted.spain.friends.legacy;

import com.schibsted.spain.friends.legacy.context.SessionContext;
import com.schibsted.spain.friends.legacy.exception.SignupLegacyException;
import com.schibsted.spain.friends.legacy.model.User;
import com.schibsted.spain.friends.legacy.validate.ValidateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignupLegacyController {

  private final ValidateUser validateUser;
  private final SessionContext sessionContext;

  @Autowired
  public SignupLegacyController(ValidateUser validateUser, SessionContext sessionContext) {
    this.validateUser = validateUser;
    this.sessionContext = sessionContext;
  }

  @PostMapping
  public ResponseEntity<String> signUp(@RequestParam("username") String username, @RequestHeader("X-Password") String password) {
    final User user = validateUser.validateUser(username, password).orElseThrow(() -> new SignupLegacyException("Error username or password"));
    sessionContext.pushUser(user);
    return ResponseEntity.ok("OK");
  }
}
