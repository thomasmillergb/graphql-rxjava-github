package thomasmillergb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import thomasmillergb.model.GitHubAccessToken;

import static java.util.Optional.ofNullable;

@SpringBootApplication
@PropertySource({"classpath:application.properties", "classpath:config/${CONFIG_ENV:local}.properties"})
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public GitHubAccessToken getAccessToken(){
        return new GitHubAccessToken(ofNullable(System.getenv("ACCESS_TOKEN")).orElseThrow(() -> new NullPointerException("No Access Token")));
    }

}
