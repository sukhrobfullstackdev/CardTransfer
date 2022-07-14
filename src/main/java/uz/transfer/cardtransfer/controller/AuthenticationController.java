package uz.transfer.cardtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.transfer.cardtransfer.payload.LoginDto;
import uz.transfer.cardtransfer.payload.Message;
import uz.transfer.cardtransfer.security.JWTProvider;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    final AuthenticationManager authenticationManager;
    final JWTProvider jwtProvider;

    public AuthenticationController(AuthenticationManager authenticationManager, JWTProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Message> login(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            String token = jwtProvider.generateToken(loginDto.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(new Message(true, "You have logged in successfully!", token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Message(false, "Login or password is incorrect!"));
        }
    }
}
