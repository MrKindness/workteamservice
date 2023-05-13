package workteam.workteamservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workteam.workteamservice.entity.role.Role;
import workteam.workteamservice.exception.RoleNotFoundException;
import workteam.workteamservice.repository.RoleRepository;
import workteam.workteamservice.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return this.roleRepository.findByName(name).orElseThrow(() -> new RoleNotFoundException(name));
    }
}
