package com.recruitment.challenge.port.in;

import com.recruitment.challenge.domain.Planet;
import com.recruitment.challenge.dto.CreatePlanetDto;
import com.recruitment.challenge.dto.PaginatePlanetFilters;
import com.recruitment.challenge.dto.ReadPlanetDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PlanetPortIn {

    Page<ReadPlanetDto> findAll(int page, int perPage, PaginatePlanetFilters filters);

    Planet findById(String id);

    void deleteById(String id);

    String create(CreatePlanetDto planet);
}
