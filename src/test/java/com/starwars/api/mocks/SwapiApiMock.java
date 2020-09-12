package com.starwars.api.mocks;

import com.starwars.api.dto.SwapiPlanetDto;
import com.starwars.api.dto.SwapiResponseDto;
import org.junit.platform.commons.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;

public class SwapiApiMock {

    public static SwapiResponseDto<SwapiPlanetDto> success() {
        return new SwapiResponseDto<SwapiPlanetDto>()
                .setCount(1)
                .setResults(Collections.singletonList(SwapiApiMock.planetSuccess()));
    }

    public static SwapiPlanetDto planetSuccess() {
        return new SwapiPlanetDto()
                .setFilms(Arrays.asList("film1", "film2"));
    }
}
