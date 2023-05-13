package workteam.workteamservice.controllerAdvice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import workteam.workteamservice.dto.TextMessageDto;
import workteam.workteamservice.exception.ValidationException;

@ControllerAdvice
public class ValidationControllerAdvice {

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<TextMessageDto> handleException(RuntimeException exception) {
        TextMessageDto response = new TextMessageDto(exception.getMessage());

        return new ResponseEntity<>(response, new HttpHeaders(), ((ValidationException)exception).getHTTPStatus());
    }
}
