package com.starwars.api.mocks;

import com.starwars.api.domain.Planet;
import com.starwars.api.dto.ReadPlanetDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class PlanetMock {

    public static Planet success() {
        return new Planet()
                .setId(RandomStringUtils.randomAlphanumeric(16))
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setClimate(RandomStringUtils.randomAlphabetic(15))
                .setGround(RandomStringUtils.randomAlphabetic(5))
                .setCreatedAt(Instant.now());
    }

    public static ReadPlanetDto successReadPlanet() {
        return new ReadPlanetDto(PlanetMock.success(), new Random().nextInt(5));
    }



    public static Page<ReadPlanetDto> list(int page, int perPage) {
        return new PageImpl<>(
                Arrays.asList(PlanetMock.successReadPlanet(), PlanetMock.successReadPlanet()),
                PageRequest.of(page, perPage),
                2);
    }

    public static Page<ReadPlanetDto> listByName(int page, int perPage, String name) {
        return new PageImpl<>(
                Collections.singletonList(new ReadPlanetDto(
                        RandomStringUtils.randomAlphanumeric(16),
                        name,
                        RandomStringUtils.randomAlphabetic(15),
                        RandomStringUtils.randomAlphabetic(5),
                        new Random().nextInt(5))),
                PageRequest.of(page, perPage),
                2);
    }
}
