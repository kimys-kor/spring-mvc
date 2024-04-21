package community.demo.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SignInRequestDto(
        @NotBlank(message = "username must not be blank")
        String username,
        String password
) {
        public SignInRequestDto {
                username = username.toLowerCase();
        }
}
