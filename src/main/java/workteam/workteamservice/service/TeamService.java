package workteam.workteamservice.service;

import workteam.workteamservice.entity.team.Team;

import java.util.List;
import java.util.UUID;

public interface TeamService {

    List<Team> loadAll();

    Team loadTeamById(UUID id);

    void create(Team team);

    void update(Team team);

    void deleteById(UUID id);
}
