package community.demo.controller;

import community.demo.common.jwt.JwtProperties;
import community.demo.common.jwt.JwtTokenProvider;
import community.demo.common.random.StringSecureRandom;
import community.demo.common.response.Response;
import community.demo.common.response.ResultCode;
import community.demo.controller.dto.SignInRequestDto;
import community.demo.controller.dto.SignUpRequestDto;
import community.demo.model.User;
import community.demo.service.RefreshTokenService;
import community.demo.service.SignInUseCase;
import community.demo.service.SignUpUseCase;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final StringSecureRandom stringSecureRandom;
    private final JwtProperties jwtProperties;
    private final SignUpUseCase signUpUseCase;
    private final SignInUseCase signInUseCase;


    @PostMapping(value = "/signup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) { // valid어노테이션을 달지 않으면 객체안의 유효성체크까지 안옴
        User user = User.builder()
                .username(signUpRequestDto.username())
                .password(signUpRequestDto.password())
                .fullName(signUpRequestDto.fullName())
                .phoneNum(signUpRequestDto.phoneNum())
                .nickname(signUpRequestDto.nickname())
                .build();

        signUpUseCase.signUp(user);
    }

    @PostMapping(value = "/signin")
    public Response<Object> signIn(
            @RequestBody SignInRequestDto signInRequestDto,
            HttpServletResponse response
    ) {
        User user = signInUseCase.SignIn(signInRequestDto);

        // access token 헤더 추가
        String jwtToken = jwtTokenProvider.generateToken(user.getId(), user.getUsername());
        response.addHeader(jwtProperties.headerString(), "Bearer "+jwtToken);

        // refresh token 쿠키 추가
        String refreshToken = stringSecureRandom.next(20);
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setMaxAge(2_592_000);
        cookie.setDomain("");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);


        refreshTokenService.save(user.getUsername(), refreshToken);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }
}
