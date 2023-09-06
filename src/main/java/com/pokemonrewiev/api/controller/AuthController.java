package com.pokemonrewiev.api.controller;

import com.pokemonrewiev.api.dto.AuthResponseDto;
import com.pokemonrewiev.api.dto.LoginDto;
import com.pokemonrewiev.api.dto.RegisterDto;
import com.pokemonrewiev.api.dto.RegisterDto;
import com.pokemonrewiev.api.entity.RefreshToken;
import com.pokemonrewiev.api.entity.Role;
import com.pokemonrewiev.api.entity.UserEntity;
import com.pokemonrewiev.api.repository.RoleRepository;
import com.pokemonrewiev.api.repository.UserRepository;
import com.pokemonrewiev.api.security.CustomUserDetailService;
import com.pokemonrewiev.api.security.JWTGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
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
    private CustomUserDetailService customUserDetailService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator=jwtGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterDto registerDto){
        if(userRepository.existsByUserName(registerDto.getUserName())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setUserName(registerDto.getUserName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Role role = roleRepository.findByRoleName("USER");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerDto.getUserName(), registerDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtGenerator.generateToken(authentication);
        String refreshToken = jwtGenerator.generateRefreshToken(authentication);
        AuthResponseDto authResponseDto = new AuthResponseDto(accessToken,"Bearer ",refreshToken);

        //LoginDto loginDto = new LoginDto();
        //loginDto.setUserName(user.getUserName());
        //loginDto.setPassword(user.getPassword());
        //AuthResponseDto authResponseDto = login(loginDto);// Nurası hatalı olabilir

        return new ResponseEntity<>(authResponseDto ,HttpStatus.CREATED);


    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtGenerator.generateToken(authentication);
        String refreshToken = jwtGenerator.generateRefreshToken(authentication);
        System.out.println(accessToken);
        System.out.println(refreshToken);
        AuthResponseDto authResponseDto = new AuthResponseDto(accessToken,"Bearer ",refreshToken);
        return authResponseDto;
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AuthResponseDto> refreshToken(@RequestBody RefreshToken refreshToken){
        String token = refreshToken.getRefreshToken();
        if (StringUtils.hasText(token) && jwtGenerator.validateToken(token)){
            String userName = jwtGenerator.getUserNameFromJWT(token);

            UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
            //authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            String newAccessToken = jwtGenerator.generateToken(authenticationToken);
            AuthResponseDto authResponse = new AuthResponseDto(newAccessToken,"Bearer " ,token);
            return ResponseEntity.ok(authResponse);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private String getJWTFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
