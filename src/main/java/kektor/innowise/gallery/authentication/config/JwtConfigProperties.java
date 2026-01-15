package kektor.innowise.gallery.authentication.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.KeyPair;
import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties("gallery.security.jwt")
public class JwtConfigProperties {

    private Duration accessExpiration = Duration.ofMinutes(15);
    private Duration refreshExpiration = Duration.ofDays(3);
    private KeyPair keyPair;
    private int activeSessionLimit;

    @Autowired
    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

}
