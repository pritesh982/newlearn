package com.sharing.doc_sharing.service;

import com.sharing.doc_sharing.dto.UserRegistrationDto;
import com.sharing.doc_sharing.model.User;
import com.sharing.doc_sharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void registerUser(UserRegistrationDto dto) {
        User user = new User();
        
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("STUDENT");
        user.setStatus("ACTIVE");
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public void savePassword(User user) {
        userRepository.save(user);
    }

    public User save(UserRegistrationDto dto) {
        User user = new User();
        
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole() != null ? dto.getRole() : "STUDENT");
        user.setStatus(dto.getStatus());
        return userRepository.save(user);
    }

    public void saveOrUpdate(UserRegistrationDto dto) {
        User user;
    
        if (dto.getId() != null) {
            user = userRepository.findById(dto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        } else {
            user = new User();
        }
    
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setStatus(dto.getStatus());
        // Only set password if it's not blank
        if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
    
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
