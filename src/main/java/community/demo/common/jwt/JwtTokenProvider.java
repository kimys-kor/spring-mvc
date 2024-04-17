package community.demo.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(Long userId, String username) {
        String jwtToken = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis()+ jwtProperties.expirationTime()))
                .withClaim("username", username)
                .sign(Algorithm.HMAC512(jwtProperties.secretKey()));

        return jwtToken;
    }

    public String resolveToken(String token) {
        return JWT.require(Algorithm.HMAC512(jwtProperties.secretKey()))
                .build()
                .verify(token)
                .getClaim("username")
                .asString();
    }
}
