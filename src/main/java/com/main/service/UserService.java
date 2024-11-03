package com.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.main.dto.ChangePasswordDTO;
import com.main.dto.LoginDTO;
import com.main.dto.UserDTO;
import com.main.dto.RegisterUserDTO;
import com.main.model.User;
import com.main.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Use BCrypt password encoder

    public UserDTO getProfile(String email) {
        return userRepository.findByEmail(email)
                .map(user -> new UserDTO(user.getName(), user.getEmail()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public UserDTO updateProfile(String email, UserDTO userDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
        
        return new UserDTO(user.getName(), user.getEmail());
    }

    public void changePassword(String email, ChangePasswordDTO changePasswordDTO) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect current password");
        }
    }

    public void registerUser(RegisterUserDTO registerUserDTO) {
        if (userRepository.existsByEmail(registerUserDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }

        User user = new User();
        user.setName(registerUserDTO.getName());
        user.setEmail(registerUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword())); // Encrypt password
        
        userRepository.save(user); // Save the new user
    }
    
    public UserDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        return new UserDTO(user.getName(), user.getEmail()); // Returning user details (excluding password)
    }
}
