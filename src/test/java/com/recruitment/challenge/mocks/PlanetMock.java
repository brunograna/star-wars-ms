package com.recruitment.challenge.mocks;

import com.recruitment.challenge.domain.Planet;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

public class PlanetMock {

    public static Planet success() {
        return new Planet()
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setClimate(RandomStringUtils.randomAlphabetic(15))
                .setGround(RandomStringUtils.randomAlphabetic(5))
                .setFilms(Arrays.asList(FilmMock.success(), FilmMock.success()))
                .setCreatedAt(Instant.now());
    }
}
