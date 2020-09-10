package com.recruitment.challenge.adapter.out;

import com.recruitment.challenge.domain.Planet;
import com.recruitment.challenge.dto.PaginatePlanetFilters;
import com.recruitment.challenge.infra.MongoPlanetRepository;
import com.recruitment.challenge.port.out.PlanetDatabasePortOut;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
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
    public Page<Planet> findAll(int page, int perPage, PaginatePlanetFilters filters) {
        var query = this.buildQuery(filters);

        var pageable = PageRequest.of(page, perPage);

        int total = Math.toIntExact(mongoTemplate.count(query, Planet.class));

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
