package workteam.workteamservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workteam.workteamservice.dto.TeamDto;
import workteam.workteamservice.facade.TeamFacade;
import workteam.workteamservice.utils.Constants;

import java.util.List;

@RestController
@RequestMapping(Constants.API.Team.root)
@CrossOrigin(origins = "http://localhost:4200")
public class TeamController {

    private final TeamFacade teamFacade;

    @Autowired
    public TeamController(TeamFacade teamFacade) {
        this.teamFacade = teamFacade;
    }

    @GetMapping(Constants.API.Team.teams)
    public ResponseEntity<List<TeamDto>> getTeams(Authentication authentication) {
        return ResponseEntity.ok().body(this.teamFacade.getTeams(authentication));
    }
}
