package workteam.workteamservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workteam.workteamservice.dto.UserDto;

import workteam.workteamservice.utils.Constants;

@RestController
@RequestMapping(Constants.API.User.root)
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @GetMapping(Constants.API.User.userDetails)
    public ResponseEntity<UserDto> getUserDetails() {
        UserDto userDto = new UserDto();
        userDto.setEmail("email");
        userDto.setName("name");
        userDto.setRole("ADMIN");
        userDto.setUsername("admin");

        return ResponseEntity.ok().body(userDto);
    }
}
