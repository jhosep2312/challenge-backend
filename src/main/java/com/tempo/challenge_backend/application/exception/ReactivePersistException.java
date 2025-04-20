package com.tempo.challenge_backend.application.exception;

public class ReactivePersistException extends RuntimeException {

    public ReactivePersistException(final String message) {
        super(message);
    }

    public ReactivePersistException(final String message, final Throwable cause) {
        super(message, cause);
    }
}

