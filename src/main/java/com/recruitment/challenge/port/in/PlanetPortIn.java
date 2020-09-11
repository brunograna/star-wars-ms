package com.recruitment.challenge.port.in;

import com.recruitment.challenge.domain.Planet;
import com.recruitment.challenge.dto.CreatePlanetDto;
import com.recruitment.challenge.dto.PaginatePlanetFilters;
import com.recruitment.challenge.dto.ReadPlanetDto;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface PlanetPortIn {

    Page<ReadPlanetDto> findAll(@NotNull PaginatePlanetFilters filters);

    ReadPlanetDto findById(@NotNull String id);

    void deleteById(@NotNull String id);

    String create(@NotNull CreatePlanetDto planet);
}
