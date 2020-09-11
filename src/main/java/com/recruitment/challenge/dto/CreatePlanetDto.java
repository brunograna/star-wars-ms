package com.recruitment.challenge.dto;

import com.recruitment.challenge.commons.SelfValidation;

import javax.validation.constraints.NotBlank;

public class CreatePlanetDto extends SelfValidation<CreatePlanetDto> {

    @NotBlank
    private final String name;

    @NotBlank
    private final String climate;

    @NotBlank
    private final String ground;

    public CreatePlanetDto(String name,
                           String climate,
                           String ground) {
        this.name = name;
        this.climate = climate;
        this.ground = ground;
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

    @Override
    public String toString() {
        return "CreatePlanetDto{" +
                "name='" + name + '\'' +
                ", climate='" + climate + '\'' +
                ", ground='" + ground + '\'' +
                '}';
    }
}
