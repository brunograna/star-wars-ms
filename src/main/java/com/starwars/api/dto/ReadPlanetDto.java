package com.starwars.api.dto;

import com.starwars.api.domain.Planet;

import java.util.List;
import java.util.stream.Collectors;

public class ReadPlanetDto {

    private String id;

    private String name;

    private String climate;

    private int filmAppearances;

    private String ground;

    public ReadPlanetDto() {
    }

    public ReadPlanetDto(Planet planet, int filmAppearances) {
        this.id = planet.getId();
        this.name = planet.getName();
        this.climate = planet.getClimate();
        this.ground = planet.getGround();
        this.filmAppearances = filmAppearances;
    }

    public ReadPlanetDto(final String id,
                         final String name,
                         final String climate,
                         final String ground,
                         final int filmAppearances) {
        this.id = id;
        this.name = name;
        this.climate = climate;
        this.ground = ground;
        this.filmAppearances = filmAppearances;
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

    public int getFilmAppearances() {
        return filmAppearances;
    }

    public ReadPlanetDto setId(String id) {
        this.id = id;
        return this;
    }

    public ReadPlanetDto setName(String name) {
        this.name = name;
        return this;
    }

    public ReadPlanetDto setClimate(String climate) {
        this.climate = climate;
        return this;
    }

    public ReadPlanetDto setFilmAppearances(int filmAppearances) {
        this.filmAppearances = filmAppearances;
        return this;
    }

    public ReadPlanetDto setGround(String ground) {
        this.ground = ground;
        return this;
    }

    @Override
    public String toString() {
        return "ReadPlanetDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", climate='" + climate + '\'' +
                ", filmAppearances=" + filmAppearances +
                ", ground='" + ground + '\'' +
                '}';
    }
}
