package com.tempo.challenge_backend.application.aspects.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReactiveLog {
}
