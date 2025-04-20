package com.tempo.challenge_backend.application;

import com.github.benmanes.caffeine.cache.Cache;
import com.tempo.challenge_backend.application.port.in.BackendService;
import com.tempo.challenge_backend.application.port.out.HistoryCallRequestRepository;
import com.tempo.challenge_backend.domain.CallRequestHistory;
import com.tempo.challenge_backend.domain.ValuesCalculate;
import com.tempo.challenge_backend.application.exception.BusinessException;
import com.tempo.challenge_backend.infrastructure.provider.PercentageProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class BackendServiceHandler implements BackendService {

    private final PercentageProvider percentageProvider;
    private final Cache<String, String> caffeineCache;
    private final HistoryCallRequestRepository historyCalRequestRepository;
    private static final String PERCENTAGE_KEY = "PERCENTAGE_KEY";
    private static final String MESSAGE_ERROR = "Error to get cache and service";


    @Override
    public Mono<ValuesCalculate> calculatePercentage(Double numberOne, Double numberTwo) {

        Double sum = numberOne + numberTwo;

        return percentageProvider.getPercentage()
                .doOnNext(response -> {
                    caffeineCache.put(PERCENTAGE_KEY, String.valueOf(response.getPercentage()));
                    log.info("calculatePercentage : saved cache ");
                })
                .map(response -> addPercentage(sum, response.getPercentage()))
                .onErrorResume(ex -> {
                    String cached = caffeineCache.getIfPresent(PERCENTAGE_KEY);
                    if (cached == null) {
                        return Mono.error(new BusinessException(MESSAGE_ERROR));
                    }
                    log.info("calculatePercentage : get from cache {}", cached);
                    return Mono.just(addPercentage(sum, Double.valueOf(cached)));
                })
                .map(numberCalculated -> ValuesCalculate.builder()
                        .numberCalculated(numberCalculated)
                        .numberOne(numberOne)
                        .numberTwo(numberTwo)
                        .build()
                );
    }

    private double addPercentage(Double base, Double percentage) {
        return base * (1 + percentage / 100);
    }

    @Override
    public Mono<String> getCaffeineKey(String caffeineKey) {
        log.info("getCaffeineKey({})", caffeineKey);
        return Mono.justOrEmpty(caffeineCache.getIfPresent(PERCENTAGE_KEY));
    }

    @Override
    public Flux<CallRequestHistory> retrieveAllCallRequest() {
        log.info("retrieveAllCallRequest()");
        return historyCalRequestRepository.getAllCallRequest();
    }

}
