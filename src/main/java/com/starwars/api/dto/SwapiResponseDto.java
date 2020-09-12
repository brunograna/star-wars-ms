package com.starwars.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SwapiResponseDto<T> {

    private int count;

    private List<T> results;

    public int getCount() {
        return count;
    }

    public SwapiResponseDto<T> setCount(int count) {
        this.count = count;
        return this;
    }

    public List<T> getResults() {
        return results;
    }

    public SwapiResponseDto<T> setResults(List<T> results) {
        this.results = results;
        return this;
    }

    @Override
    public String toString() {
        return "SwapiResponseDto{" +
                "count=" + count +
                ", results=" + results +
                '}';
    }
}
