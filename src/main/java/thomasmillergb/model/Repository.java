package thomasmillergb.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Repository {
    private String name;
    private String fullName;
    private String description;
    private String startedUrl;
    private boolean privateRepo;


    @JsonCreator
    public Repository(
            @JsonProperty("name") final String name,
            @JsonProperty("full_name") final String fullName,
            @JsonProperty("description") final String description,
            @JsonProperty("stargazers_url") final String startedUrl,
            @JsonProperty("private") final boolean privateRepo) {
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.startedUrl = startedUrl;
        this.privateRepo = privateRepo;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public String getStartedUrl() {
        return startedUrl;
    }

    public boolean isPrivateRepo() {
        return privateRepo;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setStartedUrl(final String startedUrl) {
        this.startedUrl = startedUrl;
    }

    public void setPrivateRepo(final boolean privateRepo) {
        this.privateRepo = privateRepo;
    }
}
