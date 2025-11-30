package kektor.innowise.gallery.authentication.webclient;

import kektor.innowise.gallery.authentication.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
@CacheConfig(cacheNames = CacheableUserService.USERS_CACHE)
public class CacheableUserService implements UserServiceClient {

    public static final String USERS_CACHE = "users";

    private final UserServiceClient userServiceClient;

    @Override
    @Cacheable(key = "'by_username:' + #username")
    public Optional<UserDto> fetchUser(String username) {
        return userServiceClient.fetchUser(username);
    }

    @Override
    @Cacheable(key = "'by_id:' + #userId")
    public Optional<UserDto> fetchUser(Long userId) {
        return userServiceClient.fetchUser(userId);
    }
}
