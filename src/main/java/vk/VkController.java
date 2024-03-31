package vk;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import user.*;
import vk.Responses.UserResponseEntity;

@RestController

public class VkController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    VkController(JwtTokenProvider jwtTokenProvider,
                 AuthenticationManager authenticationManager,
                 UserService userService
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping("/feed")
    void getFeed() {
    }

    @PostMapping("/register")
    public UserResponseEntity register(@RequestBody User user) {
        String passwordCheckStatus;
        int score = UserService.checkPassword(user.getPassword());
        System.out.println(score + user.getPassword() );
        if (score >= 40 && score < 60) {
            passwordCheckStatus = "good";
        } else {
            passwordCheckStatus = "perfect";
        }
        long id = userService.register(user);
        return new UserResponseEntity(id, passwordCheckStatus);
    }

    @PostMapping("/authorize")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(userService.getUserByEmail(loginRequest.getEmail()));
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }
}
