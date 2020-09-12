package com.starwars.api.mocks;

import com.starwars.api.domain.Planet;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.Arrays;

public class PlanetMock {

    public static Planet success() {
        return new Planet()
                .setId(RandomStringUtils.randomAlphanumeric(16))
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setClimate(RandomStringUtils.randomAlphabetic(15))
                .setGround(RandomStringUtils.randomAlphabetic(5))
                .setFilms(Arrays.asList(FilmMock.success(), FilmMock.success()))
                .setCreatedAt(Instant.now());
    }

    public static Page<Planet> list(int page, int perPage) {
        return new PageImpl<>(
                Arrays.asList(PlanetMock.success(), PlanetMock.success()),
                PageRequest.of(page, perPage),
                2);
    }
}