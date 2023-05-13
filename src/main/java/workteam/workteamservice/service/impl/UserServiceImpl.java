package workteam.workteamservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workteam.workteamservice.entity.team.Team;
import workteam.workteamservice.entity.user.User;
import workteam.workteamservice.exception.UserNotFoundException;
import workteam.workteamservice.exception.ValidationException;
import workteam.workteamservice.repository.UserRepository;
import workteam.workteamservice.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> loadColleagues(String username) {
        User caller = this.loadUserByUsername(username);
        List<User> users = this.userRepository.findByIdNot(caller.getId());

        if(Objects.equals(caller.getRoles().iterator().next().getName(), "ADMIN")) {
            return users;
        }

        return users.stream().filter(user -> {
            for(Team userTeam : user.getTeams()){
                if(caller.getTeams().contains(userTeam)) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void create(User user) {
        validateCreateUser(user);

        this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        User user = this.loadUserByUsername(username);
        this.userRepository.deleteById(user.getId());
    }

    private void validateCreateUser(User user) {
        if(Objects.nonNull(user.getId())) {
            throw new ValidationException("Invalid id!");
        }

        if(this.userRepository.existsByUsername(user.getUsername())) {
            throw new ValidationException("Invalid username!");
        }

        if(this.userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException("Invalid email!");
        }

        if(user.getRoles().isEmpty()) {
            throw new ValidationException("Invalid role!");
        }
    }
}
