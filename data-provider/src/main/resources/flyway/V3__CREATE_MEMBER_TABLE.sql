CREATE TABLE MEMBER(
    UUID uuid PRIMARY KEY,
    VERSION INT NOT NULL,
    NICKNAME VARCHAR(200) NOT NULL,
    PLANNING_SESSION_ID uuid NOT NULL,
    CREATED_DATE TIMESTAMP WITH TIME ZONE NOT NULL,
    LAST_MODIFIED_DATE TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT FK_PLANNING_SESSION_MEMBER FOREIGN KEY (PLANNING_SESSION_ID) REFERENCES PLANNING_SESSION(UUID)
)