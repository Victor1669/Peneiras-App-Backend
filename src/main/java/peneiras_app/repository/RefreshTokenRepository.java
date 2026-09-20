package peneiras_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peneiras_app.entity.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);
}