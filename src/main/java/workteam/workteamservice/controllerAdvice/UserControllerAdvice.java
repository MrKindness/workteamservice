package workteam.workteamservice.controllerAdvice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import workteam.workteamservice.dto.TextMessageDto;
import workteam.workteamservice.exception.UserNotFoundException;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<TextMessageDto> handleException(RuntimeException exception) {
        TextMessageDto response = new TextMessageDto(exception.getMessage());

        return new ResponseEntity<>(response, new HttpHeaders(), ((UserNotFoundException)exception).getHTTPStatus());
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<TextMessageDto> handleAuthenticationException(Exception ex) {
        TextMessageDto response = new TextMessageDto("Authentication failed");

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}