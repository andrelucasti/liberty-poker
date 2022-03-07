package com.liberty.poker.userstory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.time.LocalDate;
import java.util.UUID;

import static com.liberty.poker.userstory.UserStory.*;

@Entity(name = "USER_STORY")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
@Getter
public class UserStoryEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID uuid;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Column(name = "DESCRIPTION")
    @NonNull
    private String description;

    @Column(name = "STATUS")
    @NonNull
    private String status;

    @Column(name = "PLANNING_SESSION_ID")
    @NonNull
    private UUID planningSessionId;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private LocalDate lastModifiedDate;
}
