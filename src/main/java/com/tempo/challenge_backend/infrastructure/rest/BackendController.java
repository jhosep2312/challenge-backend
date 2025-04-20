package com.tempo.challenge_backend.infrastructure.rest;

import com.tempo.challenge_backend.application.port.in.BackendService;
import com.tempo.challenge_backend.infrastructure.rest.dto.BaseResponseDto;
import com.tempo.challenge_backend.infrastructure.rest.mapper.BaseResponseMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/backend")
public class BackendController  {

    private final BackendService backendService;

    @GetMapping("/calculate-percentage/{numberOne}/{numberTwo}")
    public Mono<BaseResponseDto> calculatePercentage(@PathVariable("numberOne") Double numberOne, @PathVariable("numberTwo") Double numberTwo) {
        log.info("method: calculatePercentage({},{})",numberOne,numberTwo);
        return backendService.calculatePercentage(numberOne,numberTwo).map(BaseResponseMapper::toResponseDtoOK);
    }

    @GetMapping("/get-caffeine-key/{caffeKey}")
    public Mono<BaseResponseDto> getCaffeineKey(@PathVariable("caffeKey") String caffeineKey) {
        log.info("method: getCaffeineKey({})",caffeineKey);
        return backendService.getCaffeineKey(caffeineKey).map(BaseResponseMapper::toResponseDtoOK);
    }

    @GetMapping("/get-all-historic-request")
    public Mono<BaseResponseDto> getAllHistoric() {
        log.info("method: getAllHistoric()");
        return backendService.retrieveAllCallRequest().collectList().map(BaseResponseMapper::toResponseDtoOK);
    }


}
