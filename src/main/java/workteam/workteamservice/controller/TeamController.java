package workteam.workteamservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workteam.workteamservice.dto.TeamDto;
import workteam.workteamservice.facade.TeamFacade;
import workteam.workteamservice.utils.Constants;

import java.util.List;
import java.util.UUID;

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

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping(value = Constants.API.Team.candidates + "/{teamId}")
    public ResponseEntity<List<String>> getCandidates(@PathVariable UUID teamId) {
        return ResponseEntity.ok().body(this.teamFacade.getCandidates(teamId));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping(value = Constants.API.Team.candidates)
    public ResponseEntity<List<String>> getAllCandidates() {
        return ResponseEntity.ok().body(this.teamFacade.getCandidates());
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createTeam(@RequestBody TeamDto teamDto) {
        this.teamFacade.createTeam(teamDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping
    public ResponseEntity<Void> updateTeam(@RequestBody TeamDto teamDto) {
        this.teamFacade.updateTeam(teamDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable UUID teamId) {
        this.teamFacade.deleteTeam(teamId);
        return ResponseEntity.ok().build();
    }
}
