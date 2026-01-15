package kektor.innowise.gallery.authentication.repository;

import kektor.innowise.gallery.authentication.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);

    void deleteAllByExpiryDateBefore(Instant before);

    int countAllByUserId(Long userId);

}
