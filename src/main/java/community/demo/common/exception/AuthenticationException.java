package community.demo.common.exception;


import community.demo.common.exception.inteface.CustomException;
import community.demo.common.exception.inteface.ErrorCode;

public class AuthenticationException extends CustomException {
	public AuthenticationException() {
		super();
	}
	
	public AuthenticationException(String message) {
		super(message);
	}
	
	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AuthenticationException(ErrorCode errorCode) {
		super(errorCode);
	}
	
	public AuthenticationException(ErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}