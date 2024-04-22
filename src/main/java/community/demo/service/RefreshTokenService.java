package community.demo.service;


import community.demo.common.exception.exceptions.AuthenticationErrorCode;
import community.demo.common.exception.exceptions.AuthenticationException;
import community.demo.common.jwt.JwtTokenProvider;
import community.demo.model.RefreshTokenEntity;
import community.demo.repository.RefreshTokenRepository;
import community.demo.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public void save(String username, String refreshToken) {

        RefreshTokenEntity tokenEntity = RefreshTokenEntity.builder()
                .refreshToken(refreshToken)
                .username(username)
                .build();

        refreshTokenRepository.deleteByUsernameEquals(username);
        refreshTokenRepository.save(tokenEntity);
    }

    public String refresh(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null) throw new AuthenticationException(AuthenticationErrorCode.UNKNOWN_ERROR);

        String oldRefreshToken = Arrays.stream(cookies)
            .filter(eachCookie -> "refresh_token".equals(eachCookie.getName()))
            .findAny()
            .orElseThrow()
            .getValue();

        RefreshTokenEntity tokenEntity = refreshTokenRepository.findById(oldRefreshToken).orElseThrow(
                AuthenticationErrorCode.UNKNOWN_REFRESH_TOKEN::defaultException);

        String username = tokenEntity.getUsername();
        Long userId = userRepository.findByUsername(username).orElseThrow(
                AuthenticationErrorCode.AUTHENTICATION_FAILED::defaultException).getId();

        String accessToken = jwtTokenProvider.generateToken(userId, username);

        return accessToken;
    }

    public boolean existsByRefreshTokenAndUsername(String refreshToken, String username) {
        return refreshTokenRepository.existsByRefreshTokenAndUsername(refreshToken, username);
    }
}
