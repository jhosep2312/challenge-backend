package com.tempo.challenge_backend.infrastructure.provider;

import com.tempo.challenge_backend.application.exception.BusinessException;
import com.tempo.challenge_backend.infrastructure.provider.dto.PercentageResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class PercentageProvider {

    private static final String MESSAGE_ERROR = "Error to get percentage";
    private final Random random = new Random();
    private final double failureRate = 0.6; //Aqui se asigna el porcentaje de fallo del mock

    public Mono<PercentageResponse> getPercentage() {


        if (random.nextDouble() < failureRate) {
            return Mono.error(new BusinessException(MESSAGE_ERROR));
        }

        double percentage = 50.0;

        return Mono.just(new PercentageResponse(
                        true,
                        percentage,
                        LocalDateTime.now()));
    }
}
