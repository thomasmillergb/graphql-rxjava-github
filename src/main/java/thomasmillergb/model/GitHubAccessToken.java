package thomasmillergb.model;

public class GitHubAccessToken {
    private final String accessToken;

    public GitHubAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
