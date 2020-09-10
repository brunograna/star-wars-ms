package com.recruitment.challenge.dto;

import com.recruitment.challenge.domain.Planet;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

public class ReadPlanetDto {

    private final String name;

    private final String climate;

    private final String ground;

    private final List<ReadFilmDto> films;

    public ReadPlanetDto(Planet planet) {
        this.name = planet.getName();
        this.climate = planet.getClimate();
        this.ground = planet.getGround();
        this.films = planet.getFilms().stream()
                                        .map(ReadFilmDto::new)
                                        .collect(Collectors.toList());
    }

    public ReadPlanetDto(final String name,
                         final String climate,
                         final String ground,
                         final List<ReadFilmDto> films) {
        this.name = name;
        this.climate = climate;
        this.ground = ground;
        this.films = films;
    }

    public String getName() {
        return name;
    }

    public String getClimate() {
        return climate;
    }

    public String getGround() {
        return ground;
    }

    public List<ReadFilmDto> getFilms() {
        return films;
    }
}
