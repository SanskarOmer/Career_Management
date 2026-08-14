package in.sanskar.careerManagement.user.service;


import in.sanskar.careerManagement.exception.EmailAlreadyExistsException;
import in.sanskar.careerManagement.user.dto.UserRegistrationRequest;
import in.sanskar.careerManagement.user.dto.UserResponse;
import in.sanskar.careerManagement.user.entity.User;
import in.sanskar.careerManagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse register(UserRegistrationRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }
}
