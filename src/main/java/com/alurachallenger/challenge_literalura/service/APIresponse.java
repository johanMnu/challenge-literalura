package com.alurachallenger.challenge_literalura.service;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APIresponse {
    private List<DatoLibro> results;

    public List<DatoLibro> getResults() {
        return results;
    }
    public void setResults(List<DatoLibro> results) {
        this.results = results;
    }

}

