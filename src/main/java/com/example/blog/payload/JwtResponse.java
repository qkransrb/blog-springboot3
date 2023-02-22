package com.example.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String accessToken;
    private String type = "Bearer";

    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public static JwtResponse of(String accessToken) {
        return new JwtResponse(Objects.requireNonNull(accessToken, "Access Token is null"));
    }
}
