package in.sanskar.careerManagement.user.controller;


import in.sanskar.careerManagement.user.dto.UserRegistrationRequest;
import in.sanskar.careerManagement.user.dto.UserResponse;
import in.sanskar.careerManagement.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody UserRegistrationRequest request
    ) {

        UserResponse response = userService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            Authentication authentication
    ) {
        String email = authentication.getName();

        UserResponse user = userService.getUserByEmail(email);

        return ResponseEntity.ok(user);
    }
}