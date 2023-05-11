package workteam.workteamservice.service;

import workteam.workteamservice.entity.user.User;

public interface UserService {

    User loadUserByUsername(String username);
}
