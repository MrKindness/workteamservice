package workteam.workteamservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import workteam.workteamservice.entity.user.UserDetailsWrapper;
import workteam.workteamservice.service.UserService;

public class WorkTeamUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsWrapper(this.userService.loadUserByUsername(username));
    }
}
