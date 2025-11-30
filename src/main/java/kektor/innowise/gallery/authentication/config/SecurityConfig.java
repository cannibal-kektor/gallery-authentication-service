package kektor.innowise.gallery.authentication.config;

import kektor.innowise.gallery.authentication.exception.AuthKeyPairLoadingException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;

@Configuration
@EnableConfigurationProperties(JwtConfigProperties.class)
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile("!smoke")
    public KeyPair authenticationKeyPair(SslBundles sslBundles) {
        KeyStore keyStore = sslBundles.getBundle("token-signing-pair")
                .getStores()
                .getKeyStore();
        KeyPair keyPair;
        try {
            keyPair = new KeyPair(keyStore.getCertificate("ssl").getPublicKey(),
                    (PrivateKey) keyStore.getKey("ssl", null));
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new AuthKeyPairLoadingException("Failed to load authentication key pair", e);
        }
        return keyPair;
    }

    @Bean
    @Profile("smoke")
    public KeyPair emptyKeyPair() {
        return new KeyPair(null, null);
    }

}
