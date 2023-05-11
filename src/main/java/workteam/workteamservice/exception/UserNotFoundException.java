package workteam.workteamservice.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ResponseException {

    public static final String USER_NOT_FOUND_MESSAGE = "User not found!";

    public UserNotFoundException() {
        super(USER_NOT_FOUND_MESSAGE);
    }

    @Override
    public HttpStatus getHTTPStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
