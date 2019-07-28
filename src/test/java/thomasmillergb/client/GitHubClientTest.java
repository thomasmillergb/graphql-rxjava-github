package thomasmillergb.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import thomasmillergb.model.GitHubAccessToken;
import thomasmillergb.model.Repository;

import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Import(GitHubClient.class)
@PropertySource("classpath:config/${CONFIG_ENV:local}.properties")
@ExtendWith(SpringExtension.class)
class GitHubClientTest {
    private static final String URL = "https://test.api.github.com/repositories?since=%s";

    @MockBean
    private GitHubAccessToken accessToken;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private GitHubClient underTest;

    @Mock
    private Repository repository1;
    @Mock
    private Repository repository2;

    @BeforeEach
    void setUp(){

        when(accessToken.getAccessToken()).thenReturn("TOKEN");
    }

    @Test
    void testGetRepos7Days() {
        List<Repository> repositoryList = asList(repository1, repository2);
        when(restTemplate.exchange(format(URL, 7), HttpMethod.GET, requestEntity(), new ParameterizedTypeReference<List<Repository>>() {
        }))
                .thenReturn(new ResponseEntity<>(repositoryList, OK));
        List<Repository> actual = underTest.resolveRepository(7);
        assertEquals(repositoryList, actual);
    }

    @Test
    void testGetRepos1Days() {
        List<Repository> repositoryList = asList(repository1, repository2);
        when(restTemplate.exchange(format(URL, 1), HttpMethod.GET, requestEntity(), new ParameterizedTypeReference<List<Repository>>() {
        }))
                .thenReturn(new ResponseEntity<>(repositoryList, OK));
        List<Repository> actual = underTest.resolveRepository(1);
        assertEquals(repositoryList, actual);
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