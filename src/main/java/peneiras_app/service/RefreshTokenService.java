package peneiras_app.service;

import org.springframework.stereotype.Service;
import peneiras_app.entity.Clube;
import peneiras_app.entity.Player;
import peneiras_app.entity.RefreshToken;
import peneiras_app.repository.RefreshTokenRepository;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final SecureRandom secureRandom = new SecureRandom();

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(Player player) {

        RefreshToken refreshToken = createBaseToken();

        refreshToken.setPlayer(player);

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken createRefreshToken(Clube clube) {

        RefreshToken refreshToken = createBaseToken();

        refreshToken.setClube(clube);

        return refreshTokenRepository.save(refreshToken);
    }

    private RefreshToken createBaseToken() {

        byte[] randomBytes = new byte[64];

        secureRandom.nextBytes(randomBytes);

        String token = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(token);
        refreshToken.setCreatedAt(Instant.now());
        refreshToken.setExpiresAt(
                Instant.now().plus(7, ChronoUnit.DAYS)
        );
        refreshToken.setRevoked(false);

        return refreshToken;
    }

    public RefreshToken findByToken(String token) {

        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Refresh token não encontrado"
                        )
                );
    }

    public RefreshToken verifyExpiration(
            RefreshToken refreshToken
    ) {

        if (refreshToken.getExpiresAt()
                .isBefore(Instant.now())) {

            refreshTokenRepository.delete(refreshToken);

            throw new RuntimeException(
                    "Refresh token expirado"
            );
        }

        if (refreshToken.isRevoked()) {

            throw new RuntimeException(
                    "Refresh token revogado"
            );
        }

        return refreshToken;
    }

    public void revokeToken(RefreshToken refreshToken) {

        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);
    }
}