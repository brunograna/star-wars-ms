package com.starwars.api.dto;

import com.starwars.api.domain.Film;

public class ReadFilmDto {

    private final String title;

    private final String director;

    private final String openingCrawl;

    public ReadFilmDto(Film film) {
        this.title = film.getTitle();
        this.director = film.getDirector();
        this.openingCrawl = film.getOpeningCrawl();
    }

    public ReadFilmDto(final String title,
                       final String director,
                       final String openingCrawl) {
        this.title = title;
        this.director = director;
        this.openingCrawl = openingCrawl;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getOpeningCrawl() {
        return openingCrawl;
    }

    @Override
    public String toString() {
        return "ReadFilmDto{" +
                "title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", openingCrawl='" + openingCrawl + '\'' +
                '}';
    }
}
