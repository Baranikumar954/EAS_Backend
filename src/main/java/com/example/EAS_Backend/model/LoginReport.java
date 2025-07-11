package com.example.EAS_Backend.model;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("protype")
public class LoginReport {
    private String status;
    private String message;
    private String userName;
    private String role;

    public LoginReport(String status, String message, String userName, String role) {
        this.status = status;
        this.message = message;
        this.userName = userName;
        this.role = role;
    }

    public LoginReport() {

    }
}
