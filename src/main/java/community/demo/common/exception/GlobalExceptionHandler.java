package community.demo.common.exception;


import community.demo.common.exception.inteface.CustomException;
import community.demo.common.exception.inteface.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
// exception이 발생하면 핸들링해서 가져온다
@RestControllerAdvice
public final class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handlerCustomException(CustomException exception) {
        // TODO use builder
        ErrorCode errorCode = exception.errorCode;
        ErrorResponse response = new ErrorResponse(
                errorCode.name(),
                errorCode.defaultHttpStatus().value(),
                exception.getClass().getName(),
                errorCode.defaultMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, errorCode.defaultHttpStatus());
    }


}

