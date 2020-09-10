package com.recruitment.challenge.core;

import com.recruitment.challenge.domain.Planet;
import com.recruitment.challenge.dto.CreatePlanetDto;
import com.recruitment.challenge.dto.PaginatePlanetFilters;
import com.recruitment.challenge.commons.exceptions.NotFoundException;
import com.recruitment.challenge.dto.ReadPlanetDto;
import com.recruitment.challenge.port.in.PlanetPortIn;
import com.recruitment.challenge.port.out.PlanetDatabasePortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PlanetCore implements PlanetPortIn {

    private final Logger logger = LoggerFactory.getLogger(PlanetCore.class);
    private final PlanetDatabasePortOut database;

    public PlanetCore(PlanetDatabasePortOut database) {
        this.database = database;
    }

    @Override
    public Page<ReadPlanetDto> findAll(int page, int perPage, PaginatePlanetFilters filters) {

        this.logger.info("find-all; start;");

        var result = this.database.findAll(page, perPage, filters);

        this.logger.info("find-all; end; success;");

        var mappedContent = result.getContent()
                                    .stream()
                                    .map(ReadPlanetDto::new)
                                    .collect(Collectors.toList());

        return new PageImpl<>(mappedContent, result.getPageable(), result.getTotalElements());
    }

    @Override
    public ReadPlanetDto findById(String id) {
        this.logger.info("find-by-id; start; id=\"{}\";", id);

        var planet = this.database.findById(id);

        if (planet.isEmpty()) throw new NotFoundException();

        this.logger.info("find-all; end; success; id=\"{}\";", id);

        return new ReadPlanetDto(planet.get());
    }

    @Override
    public void deleteById(String id) {
        this.logger.info("delete-by-id; start; id=\"{}\";", id);

        this.database.deleteById(id);

        this.logger.info("delete-by-id; end; success; id=\"{}\";", id);
    }

    @Override
    public String create(CreatePlanetDto planet) {

        this.logger.info("create; start; planet=\"{}\";", planet);

        var planetToSave = new Planet()
                .setName(planet.getName())
                .setClimate(planet.getClimate())
                .setGround(planet.getGround());

        var created = this.database.create(planetToSave);

        this.logger.info("create; end; success; planet=\"{}\";", planet);

        return created.getId();
    }
}
