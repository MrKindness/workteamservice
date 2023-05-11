package workteam.workteamservice.exception;

import org.springframework.http.HttpStatus;

public class TeamNotFoundException extends ResponseException {

    public static final String TEAM_NOT_FOUND_MESSAGE = "Team not found!";

    public TeamNotFoundException() {
        super(TEAM_NOT_FOUND_MESSAGE);
    }

    @Override
    public HttpStatus getHTTPStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
