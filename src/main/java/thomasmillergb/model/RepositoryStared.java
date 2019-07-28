package thomasmillergb.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

public class RepositoryStared extends Repository {

    private UUID id;
    private int stars;

    public RepositoryStared(
            final UUID id,
            final String name,
            final String fullName,
            final String description,
            final String startedUrl,
            final boolean privateRepo,
            final int stars) {
        super(name, fullName, description, startedUrl, privateRepo);
        this.id = id;
        this.stars = stars;
    }

    public int getStars() {
        return stars;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public void setStars(final int stars) {
        this.stars = stars;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
