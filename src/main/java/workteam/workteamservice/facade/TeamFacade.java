package workteam.workteamservice.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import workteam.workteamservice.dto.TeamDto;
import workteam.workteamservice.entity.team.Team;
import workteam.workteamservice.entity.user.User;
import workteam.workteamservice.exception.ValidationException;
import workteam.workteamservice.service.TeamService;
import workteam.workteamservice.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TeamFacade {

    private final UserService userService;
    private final TeamService teamService;

    @Autowired
    public TeamFacade(UserService userService, TeamService teamService) {
        this.userService = userService;
        this.teamService = teamService;
    }

    @Transactional(readOnly = true)
    public List<TeamDto> getTeams(Authentication authentication) {
        User caller = this.userService.loadUserByUsername(authentication.getName());
        List<Team> teams = this.teamService.loadAll();

        if (caller.getRoles().iterator().next().getName().equals("ADMIN")) {
            return teams.stream().map(TeamDto::new).collect(Collectors.toList());
        } else {
            return caller.getTeams().stream().map(TeamDto::new).collect(Collectors.toList());
        }
    }

    @Transactional(readOnly = true)
    public List<String> getCandidates(UUID id) {
        Team team = this.teamService.loadTeamById(id);

        return this.userService.loadAll().stream().filter(user -> {
            for(User userTeam : team.getUsers()) {
                if(userTeam.getId().equals(user.getId())){
                    return false;
                }
            }
            return true;
        }).map(User::getUsername).toList();
    }

    @Transactional
    public List<String> getCandidates() {
        return this.userService.loadAll().stream().map(User::getUsername).toList();
    }

    @Transactional
    public void createTeam(TeamDto teamDto) {
        Team team = convertTeamToEntity(teamDto);
        this.teamService.create(team);
    }

    @Transactional
    public void updateTeam(TeamDto teamDto) {
        Team team = convertTeamToEntity(teamDto);
        this.teamService.update(team);
    }

    @Transactional
    public void deleteTeam(UUID id) {
        this.teamService.deleteById(id);
    }

    private Team convertTeamToEntity(TeamDto teamDto) {
        Team team = new Team();
        UUID id = null;

        if (Objects.nonNull(teamDto.getId()) && teamDto.getId().length() > 0) {
            try {
                id = UUID.fromString(teamDto.getId());
            } catch (Exception ignored) {
                throw new ValidationException("Invalid id!");
            }
        }

        team.setId(id);
        team.setName(teamDto.getName());

        Set<User> users = teamDto.getUsers().stream().map(this.userService::loadUserByUsername).collect(Collectors.toSet());
        team.setUsers(users);

        return team;
    }
}
