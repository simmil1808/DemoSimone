package it.ASDevSolution.DemoSimone.Security;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
