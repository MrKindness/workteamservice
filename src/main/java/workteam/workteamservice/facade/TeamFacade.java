package workteam.workteamservice.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import workteam.workteamservice.dto.TeamDto;
import workteam.workteamservice.entity.team.Team;
import workteam.workteamservice.entity.user.User;
import workteam.workteamservice.repository.UserRepository;
import workteam.workteamservice.service.TeamService;
import workteam.workteamservice.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamFacade {

    private final UserService userService;
    private final TeamService teamService;
    private final UserRepository userRepository;

    @Autowired
    public TeamFacade(UserService userService, TeamService teamService,
                      UserRepository userRepository) {
        this.userService = userService;
        this.teamService = teamService;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<TeamDto> getTeams(Authentication authentication) {
        User caller = this.userService.loadUserByUsername(authentication.getName());
        List<Team> teams = this.teamService.loadAll();

        if(caller.getRoles().iterator().next().getName().equals("ADMIN")) {
            return teams.stream().map(TeamDto::new).collect(Collectors.toList());
        }
        else {
            return teams.stream().filter(team -> {
                for (User user : team.getUsers()) {
                    if(user.getUsername().equals(caller.getUsername())) {
                        return true;
                    }
                }
                return false;
            }).map(TeamDto::new).collect(Collectors.toList());
        }
    }
}
