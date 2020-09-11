package com.recruitment.challenge.mocks;

import com.recruitment.challenge.domain.Film;
import com.recruitment.challenge.domain.Planet;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;

public class FilmMock {

    public static Film success() {
        return new Film()
                .setTitle(RandomStringUtils.randomAlphabetic(5))
                .setDirector(RandomStringUtils.randomAlphabetic(10))
                .setOpeningCrawl(RandomStringUtils.randomAlphabetic(50));
    }
}
