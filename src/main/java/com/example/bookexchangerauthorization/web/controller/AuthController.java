package com.example.bookexchangerauthorization.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    public AuthController() {

    }

    @PostMapping("/singup")
    public ResponseEntity<String> singUp(@RequestBody UserDto userDto) {

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) throws Exception {
        try {
            userService.login(userDto);
            final UserDetails userDetails = userService.loadUserByUsername(userDto.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
