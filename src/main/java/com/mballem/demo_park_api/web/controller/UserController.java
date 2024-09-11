package com.mballem.demo_park_api.web.controller;

import com.mballem.demo_park_api.UserService;
import com.mballem.demo_park_api.entity.User;
import com.mballem.demo_park_api.web.dto.UserCreaterDTO;
import com.mballem.demo_park_api.web.dto.UserPasswordDTO;
import com.mballem.demo_park_api.web.dto.UserResponseDTO;
import com.mballem.demo_park_api.web.dto.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "User", description = "Contains CRUD operations")
@RestController
@RequestMapping(path = "api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Creates a new user",
        responses = {
            @ApiResponse(responseCode = "201", description = "Resource created successfully",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserResponseDTO.class)))
        }
    )
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreaterDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        User savedUser = userService.salvar(user);
        UserResponseDTO responseDTO = userMapper.toDTO(savedUser);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @Operation(summary = "Retrieves all users",
        responses = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserResponseDTO.class)))
        }
    )
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<User> users = userService.buscarTudo();
        List<UserResponseDTO> userDTOs = users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.status(200).body(userDTOs);
    }

    @Operation(summary = "Retrieves a user by ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        User user = userService.buscarPorId(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        UserResponseDTO responseDTO = userMapper.toDTO(user);
        return ResponseEntity.status(200).body(responseDTO);
    }

    @Operation(summary = "Updates the password of a user",
        responses = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid password or mismatch"),
            @ApiResponse(responseCode = "404", description = "User not found")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody UserPasswordDTO updto) {
        String newPassword = updto.getNewP();
        String passwordConfirm = updto.getConfirm();

        if (newPassword == null || passwordConfirm == null) {
            return ResponseEntity.badRequest().body("Passwords cannot be null");
        }

        if (!passwordConfirm.equals(newPassword)) {
            return ResponseEntity.badRequest().body("New password and confirmation do not match");
        }

        User user = userService.buscarPorId(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (!userService.verificarSenha(updto.getActive(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Current password is incorrect");
        }

        // Atualize a senha do usuário aqui, por exemplo:
        userService.attSenha(id, newPassword);

        return ResponseEntity.ok("Password updated successfully");
    }
}
