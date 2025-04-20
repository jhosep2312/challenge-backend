package com.tempo.challenge_backend.infrastructure.repository.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public abstract class BaseEntity implements Persistable<String> {

    @Id
    private String id;

    @Column("created_at")
    private LocalDateTime createdAt;
}
