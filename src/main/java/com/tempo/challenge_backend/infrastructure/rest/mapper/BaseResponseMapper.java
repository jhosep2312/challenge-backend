package com.tempo.challenge_backend.infrastructure.rest.mapper;

import com.tempo.challenge_backend.infrastructure.rest.dto.BaseResponseDto;
import org.springframework.http.HttpStatus;

public class BaseResponseMapper {

    public static BaseResponseDto toResponseDtoOK(Object valuesCalculate){

        return BaseResponseDto.builder()
                .code(HttpStatus.OK)
                .message(HttpStatus.OK.getReasonPhrase())
                .data(valuesCalculate)
                .build();
    }

    public static BaseResponseDto toResponseDtoOK(String value){

        return BaseResponseDto.builder()
                .code(HttpStatus.OK)
                .message(HttpStatus.OK.getReasonPhrase())
                .data(value)
                .build();
    }
}
