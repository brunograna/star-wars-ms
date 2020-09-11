package com.recruitment.challenge.mocks;

import com.recruitment.challenge.domain.Film;
import org.apache.commons.lang3.RandomStringUtils;

public class FilmMock {

    public static Film success() {
        return new Film()
                .setTitle(RandomStringUtils.randomAlphabetic(5))
                .setDirector(RandomStringUtils.randomAlphabetic(10))
                .setOpeningCrawl(RandomStringUtils.randomAlphabetic(50));
    }
}
