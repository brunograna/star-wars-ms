package com.starwars.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SwapiPlanetDto {

    private List<String> films;

    public List<String> getFilms() {
        return films;
    }

    public SwapiPlanetDto setFilms(List<String> films) {
        this.films = films;
        return this;
    }

    @Override
    public String toString() {
        return "SwapiPlanetDto{" +
                "films=" + films +
                '}';
    }
}
