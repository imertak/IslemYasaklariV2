package com.pokemonrewiev.api.controller;

import com.pokemonrewiev.api.entity.UserEntity;
import com.pokemonrewiev.api.repository.UserRepository;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/get/{userName}")
    public ResponseEntity<UserEntity> getUser(@PathVariable String userName){
        UserEntity user = userRepository.findByUserName(userName);

        return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
    }
}
