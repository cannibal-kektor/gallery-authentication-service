package kektor.innowise.gallery.authentication.config;

import kektor.innowise.gallery.authentication.webclient.UserServiceClient;
import kektor.innowise.gallery.security.conf.client.ProtectedUserServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    @Bean
    public UserServiceClient userServiceClient(@ProtectedUserServiceClient RestClient userRestClient) {
        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(userRestClient))
                .build()
                .createClient(UserServiceClient.class);
    }

}
