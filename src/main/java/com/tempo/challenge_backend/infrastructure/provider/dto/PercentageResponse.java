package com.tempo.challenge_backend.infrastructure.provider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PercentageResponse {

    private boolean success;
    private double percentage;
    private LocalDateTime timestamp;

}
