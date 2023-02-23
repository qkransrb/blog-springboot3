package com.example.blog.service.impl;

import com.example.blog.entity.Role;
import com.example.blog.entity.User;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.payload.JwtResponse;
import com.example.blog.payload.LoginDto;
import com.example.blog.payload.RegisterDto;
import com.example.blog.repository.RoleRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.security.JwtTokenProvider;
import com.example.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    @Override
    public JwtResponse login(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtTokenProvider.generateToken(authentication);

        return JwtResponse.of(accessToken);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String register(RegisterDto registerDto) {
        Optional<User> optional = userRepository.findByUsernameOrEmail(registerDto.getUsername(), registerDto.getEmail());

        if (optional.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role ROLE_USER = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", "ROLE_USER"));

        User user = User.of(
                registerDto.getUsername(),
                registerDto.getPassword(),
                registerDto.getName(),
                registerDto.getEmail(),
                List.of(ROLE_USER)
        );

        userRepository.save(user);

        return "Register successfully";
    }
}
