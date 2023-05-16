package workteam.workteamservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workteam.workteamservice.dto.user.UserDto;

import workteam.workteamservice.dto.user.UserEditSelfDto;
import workteam.workteamservice.facade.UserFacade;
import workteam.workteamservice.utils.Constants;

import java.util.List;

@RestController
@RequestMapping(Constants.API.User.root)
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserFacade userFacade;

    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping(Constants.API.User.userDetails)
    public ResponseEntity<UserDto> getUserDetails(Authentication authentication) {
        return ResponseEntity.ok().body(this.userFacade.getUserDetails(authentication));
    }

    @GetMapping(Constants.API.User.colleagues)
    public ResponseEntity<List<UserDto>> getColleagues(Authentication authentication) {
        return ResponseEntity.ok().body(this.userFacade.getColleagues(authentication));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        this.userFacade.createUser(userDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto) {
        this.userFacade.updateUser(userDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(Constants.API.User.self)
    public ResponseEntity<Void> updateUserSelf(@RequestBody UserEditSelfDto userDto) {
        this.userFacade.updateUserSelf(userDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        this.userFacade.deleteUser(username);
        return ResponseEntity.ok().build();
    }
}
