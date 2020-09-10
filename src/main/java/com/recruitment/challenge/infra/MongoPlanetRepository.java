package com.recruitment.challenge.infra;

import com.recruitment.challenge.domain.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoPlanetRepository extends MongoRepository<Planet, String> {
}
