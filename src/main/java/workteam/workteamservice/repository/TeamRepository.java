package workteam.workteamservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workteam.workteamservice.entity.team.Team;

import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {

    boolean existsByName(String name);
}
