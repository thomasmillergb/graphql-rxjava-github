package thomasmillergb.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import thomasmillergb.model.RepositoryStared;

import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

@Repository
public class GitHubRepo {


    private final JdbcTemplate jdbcTemplate;
    private final String TOP_10_STARS = "select TOP(%s) stars,  id, name, fullName, description, startedUrl, privateRepo from dbo.REPO order by stars desc ";
    private final String SAVE_REPO = "insert into dbo.REPO values (?, ?, ?, ?, ?, ?, ?)";

    @Autowired
    public GitHubRepo(@Qualifier("githubJdbc") final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveRepoStar(RepositoryStared repositoryStared) {
        jdbcTemplate.update(SAVE_REPO,
                ps -> {
                    int index = 1;
                    ps.setString(index++, repositoryStared.getId().toString());
                    ps.setString(index++, repositoryStared.getName());
                    ps.setString(index++, repositoryStared.getFullName());
                    ps.setString(index++, repositoryStared.getDescription());
                    ps.setString(index++, repositoryStared.getStartedUrl());
                    ps.setNString(index++, repositoryStared.isPrivateRepo() ? "Y" : "N");
                    ps.setInt(index, repositoryStared.getStars());
                });
    }

    public List<RepositoryStared> getTopNStarted(int n) {
        return jdbcTemplate.query(format(TOP_10_STARS, n),
                (rs, rowNum) -> new RepositoryStared(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("fullName"),
                        rs.getString("description"),
                        rs.getString("startedUrl"),
                        rs.getString("privateRepo").equals("Y"),
                        rs.getInt("stars")));

    }
}
