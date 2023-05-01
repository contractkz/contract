package kz.contract.controller;


import kz.contract.entity.User;
import kz.contract.entity.auth.AuthenticationRequest;
import kz.contract.entity.auth.AuthenticationResponse;
import kz.contract.entity.auth.RegisterRequest;
import kz.contract.repository.UserRepository;
import kz.contract.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        Optional<User> userExists = userRepository.findByEmail(request.getEmail());
        if (userExists.isPresent()) {
            return ResponseEntity.ok("Пользователь с email " + request.getEmail() + " уже существует");
        }
        return ResponseEntity.ok(authenticationService.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
