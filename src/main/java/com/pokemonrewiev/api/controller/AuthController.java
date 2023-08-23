package com.pokemonrewiev.api.controller;

import com.pokemonrewiev.api.dto.AuthResponseDto;
import com.pokemonrewiev.api.dto.LoginDto;
import com.pokemonrewiev.api.dto.RegisterDto;
import com.pokemonrewiev.api.dto.RegisterDto;
import com.pokemonrewiev.api.entity.Role;
import com.pokemonrewiev.api.entity.UserEntity;
import com.pokemonrewiev.api.repository.RoleRepository;
import com.pokemonrewiev.api.repository.UserRepository;
import com.pokemonrewiev.api.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*" )
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator=jwtGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        if(userRepository.existsByUserName(registerDto.getUserName())){
            return new ResponseEntity<>("Kullanıcı adı mevcut", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setUserName(registerDto.getUserName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role role = roleRepository.findByRoleName("USER");

        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        return new ResponseEntity<>("Kullanıcı başarıyla oluşturuldu",HttpStatus.CREATED);


    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        System.out.println(token);
        return new ResponseEntity<AuthResponseDto>(new AuthResponseDto(token), HttpStatus.OK);
    }

}
