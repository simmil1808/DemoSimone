package it.ASDevSolution.DemoSimone.Security;

import lombok.Data;

@Data
public class TokenRefreshResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

    public TokenRefreshResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = "Bearer"; // assegni direttamente qui
    }
}
