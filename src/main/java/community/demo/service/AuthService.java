package community.demo.service;

import community.demo.common.exception.exceptions.AuthenticationErrorCode;
import community.demo.common.jwt.JwtTokenProvider;
import community.demo.controller.dto.SignInRequestDto;
import community.demo.model.User;
import community.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements SignUpUseCase, SignInUseCase {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signUp(User user) {
        Optional<User> findUser = userRepository.findByUsername(user.getUsername());
        if (!findUser.isEmpty()) {
            throw AuthenticationErrorCode.USER_ALREADY_EXIST.defaultException();
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public User SignIn(SignInRequestDto signInRequestDto) {
        User user = userRepository.findByUsername(signInRequestDto.username()).orElseThrow(AuthenticationErrorCode.USER_NO_EXIST::defaultException);
        if (!passwordEncoder.matches(signInRequestDto.password(), user.getPassword())) {
            throw AuthenticationErrorCode.AUTHENTICATION_FAILED.defaultException();
        }
        return user;
    }
}
