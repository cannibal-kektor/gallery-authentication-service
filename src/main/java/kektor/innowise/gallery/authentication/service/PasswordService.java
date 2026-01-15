package kektor.innowise.gallery.authentication.service;

import kektor.innowise.gallery.authentication.dto.StorePasswordRequest;
import kektor.innowise.gallery.authentication.exception.InvalidPasswordException;
import kektor.innowise.gallery.authentication.exception.PasswordNotFoundException;
import kektor.innowise.gallery.authentication.model.HashedPassword;
import kektor.innowise.gallery.authentication.repository.PasswordRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordService {

    final PasswordRepository passwordRepository;
    final PasswordEncoder passwordEncoder;

    @Transactional
    public void storeUserPassword(StorePasswordRequest request) {
        HashedPassword password;
        String hashed = passwordEncoder.encode(request.password());
        if (passwordRepository.existsById(request.userId()))
            password = new HashedPassword(request.userId(), hashed, false);
        else
            password = new HashedPassword(request.userId(), hashed, true);
        passwordRepository.save(password);
    }

    @Transactional
    public void validateUserPassword(Long userId, String password) {
        HashedPassword hashedPassword = passwordRepository.findById(userId)
                .orElseThrow(() -> new PasswordNotFoundException(userId));
        if (!passwordEncoder.matches(password, hashedPassword.getPassword())) {
            throw new InvalidPasswordException();
        }
    }

}
