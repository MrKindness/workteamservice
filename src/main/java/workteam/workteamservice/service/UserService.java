package workteam.workteamservice.service;

import workteam.workteamservice.entity.user.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User loadUserById(UUID id);

    User loadUserByUsername(String username);

    List<User> loadColleagues(String username);

    void create(User user);

    void update(User user);

    void deleteByUsername(String username);
}
