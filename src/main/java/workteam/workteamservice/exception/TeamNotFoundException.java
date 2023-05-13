package workteam.workteamservice.exception;

import org.springframework.http.HttpStatus;

public class TeamNotFoundException extends ResponseException {

    public static final String TEAM_NOT_FOUND_MESSAGE = "Team {name} not found!";

    public TeamNotFoundException(String name) {
        super(TEAM_NOT_FOUND_MESSAGE.replace("{name}", name));
    }

    @Override
    public HttpStatus getHTTPStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
