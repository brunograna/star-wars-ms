package com.starwars.api.adapter.out;

import com.starwars.api.domain.Planet;
import com.starwars.api.dto.PaginatePlanetFilters;
import com.starwars.api.infra.MongoPlanetRepository;
import com.starwars.api.port.out.PlanetDatabasePortOut;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class MongoPlanetDatabaseAdapterOut implements PlanetDatabasePortOut {

    private final MongoPlanetRepository repository;
    private final MongoTemplate mongoTemplate;

    public MongoPlanetDatabaseAdapterOut(final MongoPlanetRepository repository,
                                         final MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Planet> findAll(PaginatePlanetFilters filters) {
        var query = this.buildQuery(filters);

        int page = filters.getPage();
        int perPage = filters.getPerPage();
        int total = Math.toIntExact(mongoTemplate.count(query, Planet.class));

        var pageable = PageRequest.of(page, perPage);

        query.with(pageable);

        var result = this.mongoTemplate.find(query, Planet.class);

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public Optional<Planet> findById(String id) {
        return this.repository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        this.repository.deleteById(id);
    }

    @Override
    public Planet create(Planet planet) {
        planet.setCreatedAt(Instant.now());
        return this.repository.save(planet);
    }

    private Query buildQuery(PaginatePlanetFilters filters) {
        Query query = new Query();

        if (ObjectUtils.isNotEmpty(filters.getName())) {
            query.addCriteria(Criteria.where("name").is(filters.getName()));
        }

        return query;
    }
}
