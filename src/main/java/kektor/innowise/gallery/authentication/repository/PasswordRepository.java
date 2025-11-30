package kektor.innowise.gallery.authentication.repository;

import kektor.innowise.gallery.authentication.model.HashedPassword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends CrudRepository<HashedPassword, Long> {
}
