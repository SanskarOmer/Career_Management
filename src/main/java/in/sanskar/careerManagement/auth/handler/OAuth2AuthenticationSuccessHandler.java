package in.sanskar.careerManagement.auth.handler;

import in.sanskar.careerManagement.auth.service.JwtService;
import in.sanskar.careerManagement.user.entity.AuthProvider;
import in.sanskar.careerManagement.user.entity.User;
import in.sanskar.careerManagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OAuth2User oauth2User =
                (OAuth2User) authentication.getPrincipal();

        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String providerId = oauth2User.getAttribute("sub");

        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user != null) {

            if (user.getAuthProvider() != AuthProvider.GOOGLE) {

                response.sendError(
                        HttpServletResponse.SC_CONFLICT,
                        "An account already exists with this email. " +
                                "Please login using your existing account."
                );

                return;
            }

            if (!providerId.equals(user.getProviderId())) {

                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Google account does not match the registered account."
                );

                return;
            }

        } else {

            user = User.builder()
                    .name(name)
                    .email(email)
                    .password(null)
                    .authProvider(AuthProvider.GOOGLE)
                    .providerId(providerId)
                    .build();

            user = userRepository.save(user);
        }

        String token = jwtService.generateToken(user.getEmail());

        response.setContentType("application/json");

        response.getWriter().write(
                "{\"accessToken\":\"" + token +
                        "\",\"tokenType\":\"Bearer\"}"
        );
    }
}