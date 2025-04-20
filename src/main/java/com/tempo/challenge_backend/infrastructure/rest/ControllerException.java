package com.tempo.challenge_backend.infrastructure.rest;

import com.google.gson.Gson;
import com.tempo.challenge_backend.application.aspects.annotation.ReactiveLog;
import com.tempo.challenge_backend.application.exception.BusinessException;
import com.tempo.challenge_backend.application.port.out.HistoryCallRequestRepository;
import com.tempo.challenge_backend.domain.CallRequestHistory;
import com.tempo.challenge_backend.infrastructure.rest.dto.BaseResponseDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.springframework.http.ResponseEntity.badRequest;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class ControllerException extends ResponseEntityExceptionHandler {

    private final HistoryCallRequestRepository repository;

    @ExceptionHandler(BusinessException.class)
    public final Mono<ResponseEntity<Object>> handleAllExceptions(BusinessException ex) {
        log.info("method: handleAllExceptions()");
        return Mono.deferContextual(ctx -> {
            ServerWebExchange exchange = ctx.get(ServerWebExchange.class);
            String path = exchange.getRequest().getURI().getPath();

            return repository.save(CallRequestHistory.builder()
                            .pathEndpoint(path)
                            .arguments("[]")
                            .response(ex.getMessage())
                            .build())
                    .thenReturn(ResponseEntity.unprocessableEntity()
                            .body(BaseResponseDto.builder()
                                    .code(HttpStatus.EXPECTATION_FAILED)
                                    .message(ex.getMessage())
                                    .data(null)
                                    .build()));
        });
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<BaseResponseDto> validationException(@NonNull final Exception exception) {
        log.warn("Validation exception: '{}'", exception.getMessage());

         Mono.deferContextual(ctx -> {
            ServerWebExchange exchange = ctx.get(ServerWebExchange.class);
            String path = exchange.getRequest().getURI().getPath();

            return repository.save(CallRequestHistory.builder()
                            .pathEndpoint(path)
                            .arguments("[]")
                            .response(exception.getMessage())
                            .build())
                    .thenReturn(ResponseEntity.unprocessableEntity()
                            .body(BaseResponseDto.builder()
                                    .code(HttpStatus.EXPECTATION_FAILED)
                                    .message(exception.getMessage())
                                    .data(null)
                                    .build()));
        }).subscribe();

        return badRequest().body(BaseResponseDto.builder()
                .code(HttpStatus.EXPECTATION_FAILED)
                .message(exception.getMessage())
                .data(null)
                .build());
    }

}
