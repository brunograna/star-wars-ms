package com.recruitment.challenge.port.out;

import com.recruitment.challenge.domain.Planet;
import com.recruitment.challenge.dto.PaginatePlanetFilters;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PlanetDatabasePortOut {

    Page<Planet> findAll(PaginatePlanetFilters filters);

    Optional<Planet> findById(String id);

    void deleteById(String id);

    Planet create(Planet planet);
}
