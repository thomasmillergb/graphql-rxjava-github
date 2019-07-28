package thomasmillergb.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import thomasmillergb.model.GitHubAccessToken;
import thomasmillergb.model.Repository;

import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class GitHubClient {
    private final RestTemplate restTemplate;
    private final GitHubAccessToken accessToken;
    private final String baseUrl;

    private static final String REPOSITORIES = "/repositories?since=%s";

    @Autowired
    public GitHubClient(final RestTemplate restTemplate,
                        final GitHubAccessToken accessToken,
                        @Value("${github.baseUrl}") final String baseUrl) {
        this.restTemplate = restTemplate;
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }

    public List<Repository> resolveRepository(int since) {

        //TODO to Impalement paging
        return restTemplate.exchange(baseUrl + format(REPOSITORIES, since), HttpMethod.GET, requestEntity(), new ParameterizedTypeReference<List<Repository>>() {
        }).getBody();
    }

    public int stargazersCount(String url) {
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity(), new ParameterizedTypeReference<List<Object>>() {
        }).getBody().size();
    }

    private <T> HttpEntity<T> requestEntity() {
        return new HttpEntity<>(getHttpHeaders());
    }


    private HttpHeaders getHttpHeaders() {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        requestHeaders.add(CACHE_CONTROL, "no-cache");
        requestHeaders.add(AUTHORIZATION, "token " + accessToken.getAccessToken());
        return requestHeaders;
    }
}
