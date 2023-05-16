package workteam.workteamservice.controllerAdvice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import workteam.workteamservice.dto.ErrorMessageDto;
import workteam.workteamservice.exception.RoleNotFoundException;

@ControllerAdvice
public class RoleControllerAdvice {

    @ExceptionHandler({RoleNotFoundException.class})
    public ResponseEntity<ErrorMessageDto> handleException(RuntimeException exception) {
        ErrorMessageDto response = new ErrorMessageDto(exception.getMessage());

        return new ResponseEntity<>(response, new HttpHeaders(), ((RoleNotFoundException)exception).getHTTPStatus());
    }
}
