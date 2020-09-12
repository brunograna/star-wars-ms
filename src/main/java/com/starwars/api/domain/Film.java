package com.starwars.api.domain;

public class Film {

    private String title;

    private String director;

    private String openingCrawl;

    public String getTitle() {
        return title;
    }

    public Film setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDirector() {
        return director;
    }

    public Film setDirector(String director) {
        this.director = director;
        return this;
    }

    public String getOpeningCrawl() {
        return openingCrawl;
    }

    public Film setOpeningCrawl(String openingCrawl) {
        this.openingCrawl = openingCrawl;
        return this;
    }
}
