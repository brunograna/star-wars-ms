package com.recruitment.challenge.port.in;

import com.recruitment.challenge.dto.CreatePlanetDto;
import com.recruitment.challenge.dto.PaginatePlanetFilters;
import com.recruitment.challenge.dto.ReadPlanetDto;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface PlanetPortIn {

    Page<ReadPlanetDto> findAll(@NotNull PaginatePlanetFilters filters);

    ReadPlanetDto findById(@NotBlank String id);

    void deleteById(@NotBlank String id);

    String create(@NotNull CreatePlanetDto planet);
}
