package com.example.gm1.livescore;

public class SignInResponse {
    private boolean status;
    private String message;
    private User data;

    public SignInResponse(boolean status, String message, User data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public User getData() {
        return data;
    }
}
