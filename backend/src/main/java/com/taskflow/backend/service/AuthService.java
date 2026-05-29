package com.taskflow.backend.service;

import com.taskflow.backend.dto.*;
import com.taskflow.backend.model.User;
import com.taskflow.backend.repository.UserRepository;
import com.taskflow.backend.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepo, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(AuthRequest req) {
        if (userRepo.existsByEmail(req.getEmail()))
            throw new RuntimeException("Email ya registrado");
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        User saved = userRepo.save(user);
        return new AuthResponse(jwtUtil.generateToken(saved.getId()), saved.getId());
    }

    public AuthResponse login(AuthRequest req) {
        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));
        if (!encoder.matches(req.getPassword(), user.getPassword()))
            throw new RuntimeException("Credenciales incorrectas");
        return new AuthResponse(jwtUtil.generateToken(user.getId()), user.getId());
    }
}