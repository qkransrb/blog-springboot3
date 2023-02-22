package com.example.blog.service;

import com.example.blog.payload.JwtResponse;
import com.example.blog.payload.LoginDto;
import com.example.blog.payload.RegisterDto;

public interface AuthService {

    JwtResponse login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
