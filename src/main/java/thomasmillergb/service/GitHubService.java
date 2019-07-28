package thomasmillergb.service;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thomasmillergb.client.GitHubClient;
import thomasmillergb.model.Repository;
import thomasmillergb.model.RepositoryStared;
import thomasmillergb.repository.GitHubRepo;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

import static io.reactivex.Flowable.fromIterable;
import static io.reactivex.Flowable.just;


@Service
public class GitHubService {
    private Logger logger = LoggerFactory.getLogger(GitHubService.class);
    private GitHubClient gitHubClient;

    private GitHubRepo gitHubRepo;

    @Autowired
    public GitHubService(final GitHubClient gitHubClient, final GitHubRepo gitHubRepo) {
        this.gitHubClient = gitHubClient;
        this.gitHubRepo = gitHubRepo;
    }

    @PostConstruct
    public void initService() {
        //I would defo not do it this way in product, i would have exposed this as a endpoint, may via graphql
        storeLastNDayOfRepos(7);
    }

    public void storeLastNDayOfRepos(int n) {
        //TODO to Impalement paging
        //TODO Testing, i have never used RxJava in a project that i needed to unit test, as its always been a libary i want to play with.
        // However for this problem its really cool lib to use! The reason for is there are many urls, which come back in the repo response where i need to chain cool to get the stars.
        // Using RxJava allows me to easily do it muiltiple threads but if i wanted to cool another url in the repo repo repsonce i could just have another subscriber!
        Flowable<List<Repository>> repos = just(n).map(gitHubClient::resolveRepository);

        Flowable<RepositoryStared> repositoryStaredFlowable = repos.flatMap(emmitedRepos -> fromIterable(emmitedRepos)
                .flatMap(val -> just(val)
                        .subscribeOn(Schedulers.io())
                        .map(r -> new RepositoryStared(UUID.randomUUID(), r.getName(), r.getFullName(), r.getDescription(), r.getStartedUrl(), r.isPrivateRepo(), gitHubClient.stargazersCount(r.getStartedUrl())))));


        repositoryStaredFlowable.subscribe(gitHubRepo::saveRepoStar);
        repositoryStaredFlowable.subscribe(val -> logger.info(val.toString()));

    }

    public List<RepositoryStared> getTopN(final int n) {
        return gitHubRepo.getTopNStarted(n);
    }
}
