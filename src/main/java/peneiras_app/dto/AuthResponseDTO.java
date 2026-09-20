package peneiras_app.dto;

public class AuthResponseDTO {

    private String message;
    private String accessToken;
    private String refreshToken;

    public AuthResponseDTO(
            String message,
            String accessToken,
            String refreshToken
    ) {
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getMessage() {
        return message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}