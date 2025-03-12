package com.YCDxh.model;

import lombok.Data;

@Data
public class TokenResponse<T> {
    private String token;
    private String message;
    private T data;

    public TokenResponse(String token, String message, T data) {
        this.token = token;
        this.message = message;
        this.data = data;
    }
}