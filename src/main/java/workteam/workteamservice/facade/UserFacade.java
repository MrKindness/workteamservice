package workteam.workteamservice.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import workteam.workteamservice.dto.user.UserDto;
import workteam.workteamservice.dto.user.UserEditDto;
import workteam.workteamservice.entity.role.Role;
import workteam.workteamservice.entity.user.User;
import workteam.workteamservice.exception.ValidationException;
import workteam.workteamservice.service.RoleService;
import workteam.workteamservice.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
        user.setPassword(this.passwordEncoder.encode(userDto.getUsername()));
        this.userService.create(user);
    }

    @Transactional
    public void updateUser(UserDto userDto) {
        User user = convertUserToEntity(userDto);
        this.userService.update(user);
    }

    @Transactional
    public void updateUserSelf(UserEditDto userDto) {
        User newUser = convertUserEditToEntity(userDto);
        User dbUser = this.userService.loadUserById(newUser.getId());

        if(this.passwordEncoder.matches(newUser.getPassword(), dbUser.getPassword())) {
            if(Objects.nonNull(userDto.getNewPassword()) && userDto.getNewPassword().length() > 0) {
                newUser.setPassword(this.passwordEncoder.encode(userDto.getNewPassword()));
            } else {
                newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
            }
            this.userService.update(newUser);
        }
        else {
            throw new ValidationException("Invalid password!");
        }
    }

    @Transactional
    public void deleteUser(String username) {
        this.userService.deleteByUsername(username);
    }

    private User convertUserToEntity(UserDto userDto) {
        User user = new User();
        UUID id = null;

        if(userDto.getId().length() > 0) {
            try {
                id = UUID.fromString(userDto.getId());
            } catch (Exception ignored) {
                throw new ValidationException("Invalid id!");
            }
        }

        user.setId(id);
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        Set<Role> roles = new HashSet<>(Set.of(this.roleService.findByName(userDto.getRole())));
        user.setRoles(roles);

        return user;
    }

    private User convertUserEditToEntity(UserEditDto userEditDto) {
        User user = new User();
        UUID id = null;

        if(userEditDto.getId().length() > 0) {
            try {
                id = UUID.fromString(userEditDto.getId());
            } catch (Exception ignored) {
                throw new ValidationException("Invalid id!");
            }
        }

        if(Objects.nonNull(userEditDto.getPassword()) && userEditDto.getPassword().length() > 0) {
            user.setPassword(userEditDto.getPassword());
        }
        else {
            throw new ValidationException("Invalid password!");
        }

        user.setId(id);
        user.setUsername(userEditDto.getUsername());
        user.setName(userEditDto.getName());
        user.setEmail(userEditDto.getEmail());
        user.setRoles(null);

        return user;
    }
}
