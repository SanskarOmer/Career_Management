package in.sanskar.careerManagement.auth.service;

import in.sanskar.careerManagement.auth.dto.LoginRequest;
import in.sanskar.careerManagement.auth.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        try {

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    );

            authenticationManager.authenticate(authenticationToken);

            String token =
                    jwtService.generateToken(request.getEmail());

            return LoginResponse.builder()
                    .accessToken(token)
                    .tokenType("Bearer")
                    .build();

        } catch (AuthenticationException ex) {

            throw new BadCredentialsException(
                    "Invalid email or password"
            );
        }
    }
}