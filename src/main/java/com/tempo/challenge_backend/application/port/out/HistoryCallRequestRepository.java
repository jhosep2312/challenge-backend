package com.tempo.challenge_backend.application.port.out;

import com.tempo.challenge_backend.domain.CallRequestHistory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HistoryCallRequestRepository {

    Flux<CallRequestHistory> getAllCallRequest();
    Mono<CallRequestHistory> save(CallRequestHistory callRequestHistory);
}
