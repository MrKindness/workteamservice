package workteam.workteamservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workteam.workteamservice.dto.auth.AuthResponseDto;
import workteam.workteamservice.utils.Constants;

@RestController
@RequestMapping(Constants.API.root)
public class TestController {

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping("/user")
    public ResponseEntity<AuthResponseDto> helloUser() {
        AuthResponseDto responseDto = new AuthResponseDto();
        responseDto.setToken("Hello User!");

        return ResponseEntity.ok().body(responseDto);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<AuthResponseDto> helloAdmin() {
        AuthResponseDto responseDto = new AuthResponseDto();
        responseDto.setToken("Hello User!");

        return ResponseEntity.ok().body(responseDto);
    }
}
