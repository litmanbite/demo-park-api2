package com.mballem.demo_park_api.web.controller;

import com.mballem.demo_park_api.UserService;
import com.mballem.demo_park_api.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User u = userService.salvar(user);
        return ResponseEntity.status(201).body(u);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User u = userService.buscarPorId(user);
        return ResponseEntity.status(200).body(u);
    }
}