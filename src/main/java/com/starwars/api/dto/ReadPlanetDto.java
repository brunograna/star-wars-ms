package com.starwars.api.dto;

import com.starwars.api.domain.Planet;

import java.util.List;
import java.util.stream.Collectors;

public class ReadPlanetDto {

    private final String id;

    private final String name;

    private final String climate;

    private final String ground;

    private final List<ReadFilmDto> films;

    public ReadPlanetDto(Planet planet) {
        this.id = planet.getId();
        this.name = planet.getName();
        this.climate = planet.getClimate();
        this.ground = planet.getGround();
        this.films = planet.getFilms().stream()
                                        .map(ReadFilmDto::new)
                                        .collect(Collectors.toList());
    }

    public ReadPlanetDto(final String id,
                        final String name,
                         final String climate,
                         final String ground,
                         final List<ReadFilmDto> films) {
        this.id = id;
        this.name = name;
        this.climate = climate;
        this.ground = ground;
        this.films = films;
    }

    public String getId() {
        return id;
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

    @Override
    public String toString() {
        return "ReadPlanetDto{" +
                "name='" + name + '\'' +
                ", climate='" + climate + '\'' +
                ", ground='" + ground + '\'' +
                ", films=" + films +
                '}';
    }
}
