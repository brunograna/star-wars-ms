package com.recruitment.challenge.dto;

import com.recruitment.challenge.commons.SelfValidation;

import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;

public class PaginatePlanetFilters extends SelfValidation<PaginatePlanetFilters> {

    @PositiveOrZero
    private final int page;

    @PositiveOrZero
    @Max(50)
    private final int perPage;

    private final String name;

    public PaginatePlanetFilters(int page, int perPage) {
        this.page = page;
        this.perPage = perPage;
        this.name = null;
    }

    public PaginatePlanetFilters(int page, int perPage, String name) {
        this.page = page;
        this.perPage = perPage;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPage() {
        return page;
    }

    public int getPerPage() {
        return perPage;
    }

    @Override
    public String toString() {
        return "PaginatePlanetFilters{" +
                "name='" + name + '\'' +
                '}';
    }
}
