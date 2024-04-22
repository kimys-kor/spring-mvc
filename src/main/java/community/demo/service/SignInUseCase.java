package community.demo.service;

import community.demo.controller.dto.SignInRequestDto;
import community.demo.model.User;

import java.util.Optional;

public interface SignInUseCase {

    User SignIn(SignInRequestDto signInRequestDto);
}
