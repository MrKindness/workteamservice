package workteam.workteamservice.dto;

import workteam.workteamservice.entity.team.Team;
import workteam.workteamservice.entity.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class TeamDto {

    private String id;

    private String name;

    private List<String> users;

    public TeamDto(Team team) {
        this.id = team.getId().toString();
        this.name = team.getName();
        this.users = team.getUsers().stream().map(User::getUsername).collect(Collectors.toList());
    }

    public TeamDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
