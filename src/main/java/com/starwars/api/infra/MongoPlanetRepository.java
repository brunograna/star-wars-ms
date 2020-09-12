package com.starwars.api.infra;

import com.starwars.api.domain.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoPlanetRepository extends MongoRepository<Planet, String> {
}
