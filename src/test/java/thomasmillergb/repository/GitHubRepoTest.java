package thomasmillergb.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import thomasmillergb.model.RepositoryStared;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


//TODO if i had a choice i would use h2 database as i choose sqlserver docker container to try can get the bonuns points even though h2 has a sqlserver mode ;)
//
//TODO to improve i would have had a flyway test migration script which pre populates some know vaules for this test, instead i went with save it get it
@EnableAutoConfiguration
@ComponentScan(basePackages = {"thomasmillergb.repository"})
@PropertySource("classpath:config/local.properties")
@ExtendWith(SpringExtension.class)
class GitHubRepoTest {


    @Autowired
    private GitHubRepo underTest;

    @Autowired
    @Qualifier("githubJdbc")
    private JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setup() {
        jdbcTemplate.execute("delete from dbo.REPO");
    }

    @Test
    void saveRepoAndGetStar() {

        underTest.saveRepoStar(new RepositoryStared(UUID.randomUUID(), "", "", "", "", false, 100));

        List<RepositoryStared> results = underTest.getTopNStarted(2);
        assertEquals(1, results.size());
        assertEquals(100, results.get(0).getStars());
    }

    @Test
    void saveRepo2AndGetStar2InOrder() {

        underTest.saveRepoStar(new RepositoryStared(UUID.randomUUID(), "", "", "", "", false, 99));
        underTest.saveRepoStar(new RepositoryStared(UUID.randomUUID(), "", "", "", "", false, 100));

        List<RepositoryStared> results = underTest.getTopNStarted(3);
        assertEquals(2, results.size());
        assertEquals(100, results.get(0).getStars());
        assertEquals(99, results.get(1).getStars());
    }

    @Test
    void saveRepo3AndGetStar2InOrder() {

        underTest.saveRepoStar(new RepositoryStared(UUID.randomUUID(), "", "", "", "", false, 2));
        underTest.saveRepoStar(new RepositoryStared(UUID.randomUUID(), "", "", "", "", false, 99));
        underTest.saveRepoStar(new RepositoryStared(UUID.randomUUID(), "", "", "", "", false, 100));

        List<RepositoryStared> results = underTest.getTopNStarted(2);
        assertEquals(2, results.size());
        assertEquals(100, results.get(0).getStars());
        assertEquals(99, results.get(1).getStars());
    }
}