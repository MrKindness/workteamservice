package workteam.workteamservice.service;

import workteam.workteamservice.entity.role.Role;

public interface RoleService {
    Role findByName(String name);
}
