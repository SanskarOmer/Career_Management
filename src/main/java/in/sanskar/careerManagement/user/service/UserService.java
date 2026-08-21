package in.sanskar.careerManagement.user.service;

import in.sanskar.careerManagement.exception.EmailAlreadyExistsException;
import in.sanskar.careerManagement.exception.ResourceNotFoundException;
import in.sanskar.careerManagement.user.dto.UserRegistrationRequest;
import in.sanskar.careerManagement.user.dto.UserResponse;
import in.sanskar.careerManagement.user.entity.AuthProvider;
import in.sanskar.careerManagement.user.entity.User;
import in.sanskar.careerManagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(UserRegistrationRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email is already registered"
            );
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .authProvider(AuthProvider.LOCAL)
                .build();

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    public UserResponse getUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", email)
                );

        return mapToResponse(user);
    }

    private UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }
}