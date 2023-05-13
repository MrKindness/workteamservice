package workteam.workteamservice.service;

import workteam.workteamservice.entity.user.User;

import java.util.List;

public interface UserService {

    User loadUserByUsername(String username);

    List<User> loadColleagues(String username);

    void create(User user);

    void deleteByUsername(String username);
}
