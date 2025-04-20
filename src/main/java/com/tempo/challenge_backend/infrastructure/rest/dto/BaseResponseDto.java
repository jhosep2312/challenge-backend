package com.tempo.challenge_backend.infrastructure.rest.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class BaseResponseDto{

    private HttpStatus code;
    private String message;
    private Object data;

}
