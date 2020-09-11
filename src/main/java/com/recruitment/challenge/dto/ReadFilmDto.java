package com.recruitment.challenge.dto;

import com.recruitment.challenge.domain.Film;

public class ReadFilmDto {

    private String title;

    private String director;

    private String openingCrawl;

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
