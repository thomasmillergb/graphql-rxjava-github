CREATE TABLE dbo.REPO
(
    id          VARCHAR(100) PRIMARY KEY,
    name        VARCHAR(100) NOT NULL index idx_name,
    fullName    VARCHAR(100) NOT NULL index idx_fullname,
    description VARCHAR(8000),
    startedUrl  VARCHAR(100),
    privateRepo NVARCHAR(1) NOT NULL index idx_private_repo,
    stars       INT          NOT NULL index idx_stars,
);


