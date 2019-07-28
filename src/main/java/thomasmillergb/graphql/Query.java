package thomasmillergb.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thomasmillergb.model.RepositoryStared;
import thomasmillergb.service.GitHubService;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    private GitHubService gitHubService;

    @Autowired
    public Query(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    public List<RepositoryStared> getTopStars(int count) {
        return gitHubService.getTopN(count);
    }
}