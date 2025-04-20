package com.tempo.challenge_backend.infrastructure.repository;

import com.tempo.challenge_backend.application.port.out.HistoryCallRequestRepository;
import com.tempo.challenge_backend.domain.CallRequestHistory;
import com.tempo.challenge_backend.infrastructure.repository.mapper.HistoryCallRequestMapper;
import com.tempo.challenge_backend.infrastructure.repository.r2dbc.CallRequestHistoryR2dbcRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;


@Slf4j
@Repository
@AllArgsConstructor
public class HistoryCallRequestRepositoryHandler implements HistoryCallRequestRepository {

    private final CallRequestHistoryR2dbcRepository callRequestHistoryR2dbcRepository;

    @Override
    public Flux<CallRequestHistory> getAllCallRequest() {
        log.info("getAllCallRequestAdapter");
        return callRequestHistoryR2dbcRepository.findAll().map(HistoryCallRequestMapper::toDomain);
    }

    @Override
    public Mono<CallRequestHistory> save(CallRequestHistory callRequestHistory) {
        log.info("saveAdapterRequest({})",callRequestHistory);
        return insertNative(callRequestHistory.getPathEndpoint(),callRequestHistory.getArguments(),callRequestHistory.getResponse())
                .thenReturn(CallRequestHistory.builder().build());
    }




    // Por cuestion de tiempo la insercion la hice con el template de R2dbc
    private final R2dbcEntityTemplate template;

    public Mono<Void> insertNative(String path, String args, String response) {
        String query = "INSERT INTO history_call_request (id, created_at, path_endpoint, arguments, response) " +
                "VALUES ($1, $2, $3, $4, $5)";

        UUID id = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();

        return template.getDatabaseClient()
                .sql(query)
                .bind("$1", id)
                .bind("$2", createdAt)
                .bind("$3", path)
                .bind("$4", args)
                .bind("$5", response)
                .then();
    }

}
