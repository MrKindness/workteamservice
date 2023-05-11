package workteam.workteamservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workteam.workteamservice.dto.auth.AuthResponseDto;
import workteam.workteamservice.dto.auth.AuthRequestDto;
import workteam.workteamservice.service.impl.TokenService;
import workteam.workteamservice.utils.Constants;

@RestController
@RequestMapping(Constants.API.auth)
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<AuthResponseDto> auth(@RequestBody AuthRequestDto loginRequest) {
        Authentication authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        AuthResponseDto responseDto = new AuthResponseDto();
        responseDto.setToken(this.tokenService.generateToken(authentication));

        return ResponseEntity.ok().body(responseDto);
    }
}