package com.tempo.challenge_backend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValuesCalculate {

    private Double numberOne;
    private Double numberTwo;
    private Double numberCalculated;
}
