package com.starwars.api.port.in;

import com.starwars.api.dto.CreatePlanetDto;
import com.starwars.api.dto.PaginatePlanetFilters;
import com.starwars.api.dto.ReadPlanetDto;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface PlanetPortIn {

    Page<ReadPlanetDto> findAll(@NotNull PaginatePlanetFilters filters);

    ReadPlanetDto findById(@NotBlank String id);

    void deleteById(@NotBlank String id);

    String create(@NotNull CreatePlanetDto planet);
}
