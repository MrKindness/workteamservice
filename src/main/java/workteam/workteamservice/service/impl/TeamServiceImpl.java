package workteam.workteamservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workteam.workteamservice.entity.team.Team;
import workteam.workteamservice.repository.TeamRepository;
import workteam.workteamservice.service.TeamService;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<Team> loadAll() {
        return this.teamRepository.findAll();
    }
}
