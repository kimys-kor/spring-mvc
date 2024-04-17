package community.demo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration은 @Bean이 붙은 메서드 내부에서 생성한 객체를 빈으로 등록할 수 있다는 점이다.
// 아래 코드에서 passwordEncoder를 빈으로 등록한다.
@Configuration
public class AuthenticationConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {

        //   BCryptPasswordEncoder를 상속받는 익명 자식 클래스
        return new BCryptPasswordEncoder() {
            //   상수는 모두 대문자 단어 사이에는 _ 가 규칙
            //   상수는 final을 붙이면 컴파일러가 최적화를 잘해준다.
            private static final String PREFIX = "{bcrypt}";

            @Override
            public String encode(CharSequence rawPassword) {
                return PREFIX + super.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                encodedPassword = encodedPassword.substring(PREFIX.length());
                return super.matches(rawPassword, encodedPassword);
            }
        };
    }

}
