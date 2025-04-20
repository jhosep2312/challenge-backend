package com.tempo.challenge_backend.infrastructure.repository.mapper;


import com.tempo.challenge_backend.domain.CallRequestHistory;
import com.tempo.challenge_backend.infrastructure.repository.entity.CallRequestHistoryEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class HistoryCallRequestMapper {

    public static CallRequestHistory toDomain(CallRequestHistoryEntity entity) {

        return CallRequestHistory.builder()
                .pathEndpoint(entity.getPathEndpoint())
                .arguments(entity.getArguments())
                .response(entity.getResponse())
                .build();
    }

    public static CallRequestHistoryEntity toEntity(CallRequestHistory domain) {

        return new CallRequestHistoryEntity(
                domain.getPathEndpoint(),
                domain.getArguments(),
                domain.getResponse());
    }

}
