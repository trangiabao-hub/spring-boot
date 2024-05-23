package com.example.be.api;

import com.example.be.model.request.LoginRequest;
import com.example.be.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationAPI {

    @Autowired
    AccountService accountService;


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(accountService.register(loginRequest));
    }

    @PostMapping("/login")
    public  ResponseEntity login(@RequestBody LoginRequest loginRequestDTO){
        return ResponseEntity.ok(accountService.authenticate(loginRequestDTO));
    }

    @GetMapping("/testRole")
    public ResponseEntity testRole(){
        return ResponseEntity.ok("Test Role User Successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin-only")
    public ResponseEntity admin(){
        return ResponseEntity.ok("Admin Only");
    }
}
