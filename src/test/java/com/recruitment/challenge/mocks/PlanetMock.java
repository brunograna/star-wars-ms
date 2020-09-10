package com.recruitment.challenge.mocks;

import com.recruitment.challenge.domain.Planet;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

public class PlanetMock {

    public static Planet success() {
        return new Planet()
                .setName(RandomStringUtils.random(10))
                .setClimate(RandomStringUtils.random(15))
                .setGround(RandomStringUtils.random(5))
                .setFilms(Arrays.asList(FilmMock.success(), FilmMock.success()))
                .setCreatedAt(Instant.now());
    }
}
