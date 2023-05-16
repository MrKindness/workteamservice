package workteam.workteamservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workteam.workteamservice.entity.team.Team;
import workteam.workteamservice.exception.TeamNotFoundException;
import workteam.workteamservice.exception.ValidationException;
import workteam.workteamservice.repository.TeamRepository;
import workteam.workteamservice.service.TeamService;

import java.util.List;
import java.util.UUID;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Team> loadAll() {
        return this.teamRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Team loadTeamById(UUID id) throws UsernameNotFoundException {
        return teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id.toString()));
    }

    @Override
    @Transactional
    public void create(Team team) {
        validateTeamCreate(team);

        this.teamRepository.save(team);
    }

    @Override
    @Transactional
    public void update(Team team) {
        Team dbTeam = loadTeamById(team.getId());

        validateTeamUpdate(dbTeam, team);

        dbTeam.setName(team.getName());
        dbTeam.setUsers(team.getUsers());

        this.teamRepository.save(team);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        this.teamRepository.deleteById(id);
    }

    private void validateTeamCreate(Team team) {
        if(this.teamRepository.existsByName(team.getName())) {
            throw new ValidationException("Invalid name!");
        }
    }

    private void validateTeamUpdate(Team dbTeam, Team newTeam) {
        if(!dbTeam.getName().equals(newTeam.getName()) && this.teamRepository.existsByName(newTeam.getName())) {
            throw new ValidationException("Invalid name!");
        }
    }
}
