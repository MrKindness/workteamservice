package workteam.workteamservice.service;

import workteam.workteamservice.entity.team.Team;

import java.util.List;

public interface TeamService {
    List<Team> loadAll();
}
