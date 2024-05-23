package com.example.be.service;

import com.example.be.entity.Account;
import com.example.be.model.request.LoginRequest;
import com.example.be.model.response.LoginResponse;
import com.example.be.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;



    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        UserDetails userDetails = accountRepository.findAccountByPhone(phone);
        return userDetails;
    }

    public LoginResponse register(LoginRequest request) {
        Account account = new Account();

        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setPhone(request.getPhone());
        account = accountRepository.save(account);
        return new LoginResponse(account.getPhone(), null);
    }
    public LoginResponse authenticate(LoginRequest request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getPhone(),
                            request.getPassword()
                    )
            );
        }catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException("Wrong Id Or Password") ;
        }
        Account account =accountRepository.findAccountByPhone(request.getPhone());
        String token = jwtService.generateToken(account);
        return new LoginResponse(account.getPhone(), token);
    }
}
