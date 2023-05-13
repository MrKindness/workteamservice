package workteam.workteamservice.controllerAdvice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import workteam.workteamservice.dto.TextMessageDto;
import workteam.workteamservice.exception.TeamNotFoundException;

@ControllerAdvice
public class TeamControllerAdvice {

    @ExceptionHandler({TeamNotFoundException.class})
    public ResponseEntity<TextMessageDto> handleException(RuntimeException exception) {
        TextMessageDto response = new TextMessageDto(exception.getMessage());

        return new ResponseEntity<>(response, new HttpHeaders(), ((TeamNotFoundException)exception).getHTTPStatus());
    }
}
