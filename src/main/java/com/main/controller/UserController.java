package com.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.main.dto.ChangePasswordDTO;
import com.main.dto.LoginDTO;
import com.main.dto.UserDTO;
import com.main.dto.RegisterUserDTO;
import com.main.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile/{email}")
    public ResponseEntity<UserDTO> getProfile(@PathVariable String email) {
        return ResponseEntity.ok(userService.getProfile(email));
    }

    @PutMapping("/profile/{email}")
    public ResponseEntity<UserDTO> updateProfile(@PathVariable String email, @RequestBody UserDTO userDto) {
        return ResponseEntity.ok(userService.updateProfile(email, userDto));
    }

    @PutMapping("/change-password/{email}")
    public ResponseEntity<Void> changePassword(@PathVariable String email, @RequestBody ChangePasswordDTO changePasswordDTO) {
        userService.changePassword(email, changePasswordDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        userService.registerUser(registerUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginDTO) {
        UserDTO userDTO = userService.login(loginDTO);
        return ResponseEntity.ok(userDTO);
    }

}
