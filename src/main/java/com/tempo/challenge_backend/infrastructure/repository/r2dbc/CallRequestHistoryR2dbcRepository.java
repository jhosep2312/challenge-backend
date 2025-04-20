package com.tempo.challenge_backend.infrastructure.repository.r2dbc;

import com.tempo.challenge_backend.infrastructure.repository.entity.CallRequestHistoryEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.UUID;

public interface CallRequestHistoryR2dbcRepository extends ReactiveCrudRepository<CallRequestHistoryEntity, String> {

    @Modifying
    @Query("INSERT INTO public.history_call_request (id,path_endpoint, arguments, response)\n" +
            " VALUES(:id,:pathEndpoint, :arguments, :response); ")
    Mono<CallRequestHistoryEntity> create(
            @Param("id") String id,
            @Param("pathEndpoint") String pathEndpoint,
            @Param("arguments") String arguments,
            @Param("response") String response);
}
