package in.sanskar.careerManagement.auth.controller;

import in.sanskar.careerManagement.auth.dto.LoginRequest;
import in.sanskar.careerManagement.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequest request
    ) {

        authService.authenticate(request);

        return ResponseEntity.ok("Authentication successful");
    }
}
