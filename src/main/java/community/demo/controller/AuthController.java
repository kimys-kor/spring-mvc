package community.demo.controller;

import community.demo.controller.dto.SignUpRequestDto;
import community.demo.model.User;
import community.demo.service.SignUpUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SignUpUseCase signUpUseCase;

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

}
