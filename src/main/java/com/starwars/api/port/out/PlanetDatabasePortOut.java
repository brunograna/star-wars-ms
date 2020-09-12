package com.starwars.api.port.out;

import com.starwars.api.domain.Planet;
import com.starwars.api.dto.PaginatePlanetFilters;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PlanetDatabasePortOut {

    Page<Planet> findAll(PaginatePlanetFilters filters);

    Optional<Planet> findById(String id);

    void deleteById(String id);

    Planet create(Planet planet);
}
