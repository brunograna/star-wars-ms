package com.recruitment.challenge.port.out;

import com.recruitment.challenge.domain.Planet;
import com.recruitment.challenge.dto.PaginatePlanetFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlanetDatabasePortOut {

    Page<Planet> findAll(int page, int perPage, PaginatePlanetFilters filters);

    Optional<Planet> findById(String id);

    void deleteById(String id);

    Planet create(Planet planet);
}
