package community.demo.common.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@ConfigurationProperties(prefix = "app.jwt")
// configurationProperties를 통해 객체에 바인딩 할수 있지만
// 경우에 따라 변환 논리가 복잡하거나 할경우 사용자 지정 변환 논리가 필요할수 있다
@ConfigurationPropertiesBinding
public record JwtProperties(
        String secretKey,
        Long expirationTime,
        String headerPrefix //변수명 리팩토링시 반드시 shift + f6
) {
    public JwtProperties { // compact constructor
        // this -> null yet.
        if (expirationTime == null) {
            expirationTime = 1_200_000L;
        }
        if (headerPrefix == null) {
            headerPrefix = "Bearer";
        }
    }

}
