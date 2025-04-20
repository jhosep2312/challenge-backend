package com.tempo.challenge_backend.application.aspects;


import com.google.gson.Gson;
import com.tempo.challenge_backend.application.port.out.HistoryCallRequestRepository;
import com.tempo.challenge_backend.domain.CallRequestHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class ReactiveCallRequestHistoryAspect {

    private final HistoryCallRequestRepository repository;

    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    //@Around("@annotation(com.tempo.challenge_backend.application.aspects.annotation.ReactiveLog)")
    public Object logEndpointCall(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("LOL");
        return Mono.deferContextual(ctxView -> {
            ServerWebExchange exchange = ctxView.get(ServerWebExchange.class);
            String path = exchange.getRequest().getURI().getPath();
            String args = Arrays.toString(joinPoint.getArgs());

            Object result;
            try {
                result = joinPoint.proceed();
            } catch (Throwable e) {
                return Mono.error(e);
            }

            if (result instanceof Mono<?> monoResult) {
                return monoResult.flatMap(response ->
                        repository.save(CallRequestHistory.builder()
                                        .pathEndpoint(path)
                                        .arguments(args)
                                        .response(new Gson().toJson(response))
                                        .build()
                                ).doOnSuccess(c -> log.info("[saveReactiveCallRequestHistoryAspect]"))
                                .thenReturn(response));
            } else {
                return Mono.just(result);
            }
        }).subscribeOn(Schedulers.boundedElastic());

    }
}
