package com.tempo.challenge_backend.application.port.in;

import com.tempo.challenge_backend.domain.CallRequestHistory;
import com.tempo.challenge_backend.domain.ValuesCalculate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface BackendService {

    Mono<ValuesCalculate> calculatePercentage(Double numberOne, Double numberTwo);
    Mono<String> getCaffeineKey(String caffeineKey);
    Flux<CallRequestHistory> retrieveAllCallRequest();
}
