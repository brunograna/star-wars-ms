package com.starwars.api.mocks;

import com.starwars.api.domain.Film;
import org.apache.commons.lang3.RandomStringUtils;

public class FilmMock {

    public static Film success() {
        return new Film()
                .setTitle(RandomStringUtils.randomAlphabetic(5))
                .setDirector(RandomStringUtils.randomAlphabetic(10))
                .setOpeningCrawl(RandomStringUtils.randomAlphabetic(50));
    }
}
