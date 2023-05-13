package workteam.workteamservice.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import workteam.workteamservice.dto.UserDto;
import workteam.workteamservice.entity.role.Role;
import workteam.workteamservice.entity.user.User;
import workteam.workteamservice.service.RoleService;
import workteam.workteamservice.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
public class UserFacade {

    private final RoleService roleService;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserFacade(RoleService roleService, UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public UserDto getUserDetails(Authentication authentication) {
        User user = this.userService.loadUserByUsername(authentication.getName());
        return new UserDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getColleagues(Authentication authentication) {
        return this.userService.loadColleagues(authentication.getName()).stream().map(UserDto::new).toList();
    }

    @Transactional
    public void createUser(UserDto userDto) {
        User user = convertUserToEntity(userDto);
        this.userService.create(user);
    }

    @Transactional
    public void deleteUser(String username) {
        this.userService.deleteByUsername(username);
    }

    private User convertUserToEntity(UserDto userDto) {
        User user = new User();

        UUID id = null;
        try {
            id = UUID.fromString(userDto.getId());
        } catch (Exception ignored) {
        }

        user.setId(id);
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(this.passwordEncoder.encode(userDto.getUsername()));

        Set<Role> roles = Set.of(this.roleService.findByName(userDto.getRole()));
        user.setRoles(roles);

        return user;
    }
}
