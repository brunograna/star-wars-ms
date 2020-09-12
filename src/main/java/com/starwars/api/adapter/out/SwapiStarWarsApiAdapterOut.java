package com.starwars.api.adapter.out;

import com.starwars.api.commons.exceptions.AdapterOutboundException;
import com.starwars.api.dto.SwapiPlanetDto;
import com.starwars.api.dto.SwapiResponseDto;
import com.starwars.api.port.out.StarWarsApiPortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Objects;

import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

@Service
public class SwapiStarWarsApiAdapterOut implements StarWarsApiPortOut {

    private final Logger logger = LoggerFactory.getLogger(SwapiStarWarsApiAdapterOut.class);
    private final RestTemplate restTemplate;

    @Value("${app.star-wars-api.base-url}")
    private String baseUrl;

    @Value("${app.star-wars-api.resources.planets}")
    private String planetsPath;

    public SwapiStarWarsApiAdapterOut(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public int getFilmAppearancesByPlanet(String planetName) throws AdapterOutboundException {
        try {
            logger.info("get-film-appearances-by-planet; start; planetName=\"{}\";", planetName);

            var uri = UriComponentsBuilder
                        .fromUriString(baseUrl)
                        .path(planetsPath)
                        .queryParam("search", planetName)
                        .build()
                        .toUriString();

            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            logger.info("get-film-appearances-by-planet; http-request; start; planetName=\"{}\"; uri=\"{}\";",
                    planetName, uri);

            var response = this.restTemplate.exchange(
                    uri,
                    GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<SwapiResponseDto<SwapiPlanetDto>>() {});

            logger.info("get-film-appearances-by-planet; http-request; end; success; planetName=\"{}\"; uri=\"{}\";",
                    planetName, uri);

            if (!Objects.equals(OK, response.getStatusCode())) {
                throw new AdapterOutboundException("Swapi returned " + response.getStatusCode());
            }

            var body = response.getBody();

            if (body == null) {
                throw new AdapterOutboundException("Swapi returned an empty body");
            }

            var appearances = CollectionUtils.isEmpty(body.getResults()) ? 0 : body.getResults().get(0).getFilms().size();

            logger.info("get-film-appearances-by-planet; end; success; planetName=\"{}\"; appearances=\"{}\"",
                    planetName, appearances);

            return appearances;

        } catch (Exception e) {
            logger.error("get-film-appearances-by-planet; exception; planetName=\"{}\"; message=\"{}\"; stackTrace=\"{}\";",
                    planetName, getMessage(e), getStackTrace(e));
            throw new AdapterOutboundException(getMessage(e));
        }
    }
}
