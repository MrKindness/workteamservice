package workteam.workteamservice.controllerAdvice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import workteam.workteamservice.dto.TextDto;
import workteam.workteamservice.exception.TeamNotFoundException;

@ControllerAdvice
public class TeamControllerAdvice {

    @ExceptionHandler({TeamNotFoundException.class})
    public ResponseEntity<TextDto> handleException(RuntimeException exception) {
        TextDto response = new TextDto();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}