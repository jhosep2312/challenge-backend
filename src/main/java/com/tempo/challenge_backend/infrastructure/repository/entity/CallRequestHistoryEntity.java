package com.tempo.challenge_backend.infrastructure.repository.entity;


import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.isNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("history_call_request")
@EqualsAndHashCode(callSuper = true)
public class CallRequestHistoryEntity extends BaseEntity{

    @Column("path_endpoint")
    private String pathEndpoint;

    @Column("arguments")
    private String arguments;

    @Column("response")
    private String response;

    @Override
    @Transient
    public boolean isNew() {
        final boolean result = isNull(getId());
        final LocalDateTime now = LocalDateTime.now();
        if (result) {
            setId(UUID.randomUUID().toString());
            setCreatedAt(now);
        }
        return result;
    }
}
