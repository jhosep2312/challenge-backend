package com.tempo.challenge_backend.domain;

import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CallRequestHistory {

    private String pathEndpoint;
    private String arguments;
    private String response;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
