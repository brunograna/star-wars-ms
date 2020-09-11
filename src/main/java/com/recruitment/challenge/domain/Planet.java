package com.recruitment.challenge.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "challenge-ms-planets")
public class Planet {

    @Id
    private String id;

    private String name;

    private String climate;

    private String ground;

    private List<Film> films;

    private Instant createdAt;

    private Instant updatedAt;

    public Planet setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Planet setName(String name) {
        this.name = name;
        return this;
    }

    public String getClimate() {
        return climate;
    }

    public Planet setClimate(String climate) {
        this.climate = climate;
        return this;
    }

    public String getGround() {
        return ground;
    }

    public Planet setGround(String ground) {
        this.ground = ground;
        return this;
    }

    public List<Film> getFilms() {
        if (films == null) {
            films = new ArrayList<>();
        }
        return films;
    }

    public Planet setFilms(List<Film> films) {
        this.films = films;
        return this;
    }

    public Planet setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Planet setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
