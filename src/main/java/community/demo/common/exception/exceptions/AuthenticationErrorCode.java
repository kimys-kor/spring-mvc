package community.demo.common.exception.exceptions;

import community.demo.common.exception.inteface.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthenticationErrorCode implements ErrorCode {
    /* 토큰 관련 */
    EXPIRED_REFRESH_TOKEN(HttpStatus.FORBIDDEN, "만료된 리프레시 토큰입니다."),
    UNKNOWN_REFRESH_TOKEN(HttpStatus.FORBIDDEN, "리프레시 토큰이 존재하지 않습니다."),
    UNKNOWN_ERROR(HttpStatus.FORBIDDEN, "액세스 토큰이 존재하지 않습니다."),
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "유효한 액세스 토큰이 아닙니다."),
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN, "만료된 액세스 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.FORBIDDEN, "변조된 액세스 토큰입니다."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "파라미터 형식이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러"),

    /* 유저 관련 */
    USER_ALREADY_EXIST(HttpStatus.UNAUTHORIZED, "이미 존재하는 ID입니다"),
    USER_NO_EXIST(HttpStatus.UNAUTHORIZED, "존재하지 않는 ID입니다"),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "유저 인증에 실패하였습니다"),


    ;

    public final HttpStatus status;
    public final String message;

    @Override
    public HttpStatus defaultHttpStatus() {
        return status;
    }

    @Override
    public String defaultMessage() {
        return message;
    }

    @Override
    public AuthenticationException defaultException() {
        return new AuthenticationException(this);
    }

    @Override
    public AuthenticationException defaultException(Throwable cause) {
        return new AuthenticationException(this, cause);
    }
}
