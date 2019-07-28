CREATE TABLE dbo.REPO
(
    id          VARCHAR(100) PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    fullName    VARCHAR(100),
    description VARCHAR(8000),
    startedUrl  VARCHAR(100),
    privateRepo NVARCHAR(1),
    stars       INT          NOT NULL
);


