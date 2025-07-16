package it.ASDevSolution.DemoSimone.Security;

import lombok.Data;

@Data
public class TokenRefreshRequest {

    private String refreshToken;
}
