package kektor.innowise.gallery.authentication.config;

import kektor.innowise.gallery.authentication.dto.UserDto;
import kektor.innowise.gallery.authentication.webclient.CacheableUserService;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.LoggingCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@EnableCaching
@Configuration
public class CacheConfig implements CachingConfigurer {

    @Bean
    RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        var serializationPair = RedisSerializationContext.SerializationPair.fromSerializer(
                new Jackson2JsonRedisSerializer<>(UserDto.class));
        return builder -> {
            var conf = builder.getCacheConfigurationFor(CacheableUserService.USERS_CACHE)
                    .orElse(RedisCacheConfiguration.defaultCacheConfig())
                    .serializeValuesWith(serializationPair);
            builder.withCacheConfiguration(CacheableUserService.USERS_CACHE, conf);
        };
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new LoggingCacheErrorHandler();
    }

}
