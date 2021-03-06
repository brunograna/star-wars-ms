package com.starwars.api.adapter.in;

import com.starwars.api.StarWarsMsApplication;
import com.starwars.api.commons.exceptions.NotFoundException;
import com.starwars.api.dto.CreatePlanetDto;
import com.starwars.api.dto.ErrorDto;
import com.starwars.api.dto.PaginatePlanetFilters;
import com.starwars.api.dto.ReadPlanetDto;
import com.starwars.api.mocks.HelperPage;
import com.starwars.api.mocks.PlanetMock;
import com.starwars.api.port.in.PlanetPortIn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(classes = StarWarsMsApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Http Planet API")
class HttpPlanetAdapterInTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private HttpPlanetAdapterIn planetAdapterIn;

    @Autowired
    private PlanetPortIn planetPortIn;
    @Mock
    private PlanetPortIn planetPortInMock;

    @Bean
    public TestRestTemplate restTemplate() {
        return new TestRestTemplate();
    }

    public void mockFields() {
        ReflectionTestUtils.setField(planetAdapterIn, "planetPortIn", planetPortInMock);
    }

    @Nested
    @DisplayName("[POST] - /star-wars/v1/planets")
    class Create {


        @Test
        public void create_shouldCreateWithSuccess() {
            mockFields();
            try {
                doReturn("12345").when(planetPortInMock).create(any());

                var input = new CreatePlanetDto("name", "climate", "ground");

                var response = restTemplate.exchange(
                        "/star-wars/v1/planets",
                        POST,
                        new HttpEntity<>(input),
                        String.class);

                assertNull(response.getBody());
                assertNotNull(response.getHeaders().getLocation());
                assertTrue(response.getHeaders().getLocation().toString().contains("/star-wars/v1/planets/12345"));
            } finally {
                clearMocks();
            }
        }
        @Test
        public void create_shouldBadRequestNameIsBlank() {
            try {
                doReturn("12345").when(planetPortInMock).create(any());

                var input = new CreatePlanetDto("", "climate", "ground");

                var response = restTemplate.exchange(
                        "/star-wars/v1/planets",
                        POST,
                        new HttpEntity<>(input),
                        ErrorDto.class);

                assertEquals(response.getStatusCode(), BAD_REQUEST);
                assertNotNull(response.getBody());
                assertEquals(response.getBody().getMessage(), "name must not be blank");
                assertEquals(response.getBody().getStatusCode(), BAD_REQUEST.value());
            } finally {
                clearMocks();
            }
        }
        @Test
        public void create_shouldBadRequestClimateIsBlank() {
            try {
                doReturn("12345").when(planetPortInMock).create(any());

                var input = new CreatePlanetDto("name", "", "ground");

                var response = restTemplate.exchange(
                        "/star-wars/v1/planets",
                        POST,
                        new HttpEntity<>(input),
                        ErrorDto.class);

                assertEquals(response.getStatusCode(), BAD_REQUEST);
                assertNotNull(response.getBody());
                assertEquals(response.getBody().getMessage(), "climate must not be blank");
                assertEquals(response.getBody().getStatusCode(), BAD_REQUEST.value());
            } finally {
                clearMocks();
            }
        }
        @Test
        public void create_shouldBadRequestGroundIsBlank() {
            try {
                doReturn("12345").when(planetPortInMock).create(any());

                var input = new CreatePlanetDto("name", "climate", "");

                var response = restTemplate.exchange(
                        "/star-wars/v1/planets",
                        POST,
                        new HttpEntity<>(input),
                        ErrorDto.class);

                assertEquals(response.getStatusCode(), BAD_REQUEST);
                assertNotNull(response.getBody());
                assertEquals(response.getBody().getMessage(), "ground must not be blank");
                assertEquals(response.getBody().getStatusCode(), BAD_REQUEST.value());
            } finally {
                clearMocks();
            }
        }
    }

    @Nested
    @DisplayName("[GET] - /star-wars/v1/planets/{id}")
    class FindById {
        @Test
        public void findById_shouldGetPlanet() {
            mockFields();
            try {
                var input = new ReadPlanetDto("12345", "name", "climate", "ground", 1);

                doReturn(input).when(planetPortInMock).findById(any());

                var response = restTemplate.exchange(
                        "/star-wars/v1/planets/12345",
                        GET,
                        new HttpEntity<>(new HttpHeaders()),
                        ReadPlanetDto.class);

                assertEquals(response.getStatusCode(), OK);
                assertNotNull(response.getBody());
                assertEquals(response.getBody().getClimate(), input.getClimate());
                assertEquals(response.getBody().getGround(), input.getGround());
                assertEquals(response.getBody().getName(), input.getName());
                assertEquals(response.getBody().getFilmAppearances(), input.getFilmAppearances());

            } finally {
                clearMocks();
            }
        }
        @Test
        public void findById_shouldBeNotFoundWhenPlanetNotExists() {
            mockFields();
            try {
                doThrow(new NotFoundException()).when(planetPortInMock).findById(any());

                var response = restTemplate.exchange(
                        "/star-wars/v1/planets/12345",
                        GET,
                        new HttpEntity<>(new HttpHeaders()),
                        ReadPlanetDto.class);

                assertEquals(response.getStatusCode(), NOT_FOUND);
                assertNull(response.getBody());

            } finally {
                clearMocks();
            }
        }
    }

    @Nested
    @DisplayName("[DELETE] - /star-wars/v1/planets/{id}")
    class DeleteById {
        @Test
        public void deleteById_shouldDeletePlanet() {
            mockFields();
            try {
                doNothing().when(planetPortInMock).deleteById(any());

                var response = restTemplate.exchange(
                        "/star-wars/v1/planets/12345",
                        DELETE,
                        new HttpEntity<>(new HttpHeaders()),
                        ReadPlanetDto.class);

                assertEquals(response.getStatusCode(), NO_CONTENT);
                assertNull(response.getBody());

            } finally {
                clearMocks();
            }
        }
        @Test
        public void deleteById_shouldBeNotFoundWhenPlanetNotExists() {
            mockFields();
            try {
                doThrow(new NotFoundException()).when(planetPortInMock).deleteById(any());

                var response = restTemplate.exchange(
                        "/star-wars/v1/planets/12345",
                        DELETE,
                        new HttpEntity<>(new HttpHeaders()),
                        ReadPlanetDto.class);

                assertEquals(response.getStatusCode(), NOT_FOUND);
                assertNull(response.getBody());

            } finally {
                clearMocks();
            }
        }
    }

    @Nested
    @DisplayName("[GET] - /star-wars/v1/planets")
    class FindAll {

        @Captor
        ArgumentCaptor<PaginatePlanetFilters> filtersArgumentCaptorCaptor;

        @Test
        public void findAll_shouldFindAllWhenPageAndPerPageIsNotSet() {
            mockFields();
            try {
                var coreOutput = PlanetMock.list(0, 50);
                doReturn(coreOutput).when(planetPortInMock).findAll(any());

                var response = restTemplate.exchange(
                        "/star-wars/v1/planets",
                        GET,
                        new HttpEntity<>(new HttpHeaders()),
                        new ParameterizedTypeReference<HelperPage<ReadPlanetDto>>() {});

                verify(planetPortInMock, times(1)).findAll(filtersArgumentCaptorCaptor.capture());

                var capturedFilter = filtersArgumentCaptorCaptor.getValue();

                assertEquals(capturedFilter.getPage(), 0);
                assertEquals(capturedFilter.getPerPage(), 50);
                assertTrue(StringUtils.isBlank(capturedFilter.getName()));

                assertEquals(response.getStatusCode(), OK);
                assertNotNull(response.getBody());
                assertEquals(response.getBody().getNumber(), coreOutput.getNumber());
                assertEquals(response.getBody().getSize(), coreOutput.getSize());
                assertEquals(response.getBody().getTotalElements(), coreOutput.getTotalElements());
                assertEquals(response.getBody().getNumberOfElements(), coreOutput.getNumberOfElements());

                AtomicInteger index = new AtomicInteger();
                response.getBody().getContent().forEach((planetResponse) -> {
                    assertEquals(planetResponse.getName(), coreOutput.getContent().get(index.get()).getName());
                    assertEquals(planetResponse.getGround(), coreOutput.getContent().get(index.get()).getGround());
                    assertEquals(planetResponse.getClimate(), coreOutput.getContent().get(index.get()).getClimate());
                    assertEquals(planetResponse.getFilmAppearances(), coreOutput.getContent().get(index.get()).getFilmAppearances());

                    index.getAndIncrement();
                });

            } finally {
                clearMocks();
            }
        }

        @Test
        public void findAll_shouldFindAllWhenPageAndPerPageIsSet() {
            mockFields();
            try {
                var coreOutput = PlanetMock.list(0, 50);
                doReturn(coreOutput).when(planetPortInMock).findAll(any());

                var url = UriComponentsBuilder
                        .fromUriString("/star-wars/v1/planets")
                        .queryParam("page", 1)
                        .queryParam("perPage", 2)
                        .build()
                        .toUriString();

                var response = restTemplate.exchange(
                        url,
                        GET,
                        new HttpEntity<>(new HttpHeaders()),
                        new ParameterizedTypeReference<HelperPage<ReadPlanetDto>>() {});

                verify(planetPortInMock, times(1)).findAll(filtersArgumentCaptorCaptor.capture());

                var capturedFilter = filtersArgumentCaptorCaptor.getValue();

                assertEquals(capturedFilter.getPage(), 1);
                assertEquals(capturedFilter.getPerPage(), 2);
                assertTrue(StringUtils.isBlank(capturedFilter.getName()));

                assertEquals(response.getStatusCode(), OK);
                assertNotNull(response.getBody());
                assertEquals(response.getBody().getNumber(), coreOutput.getNumber());
                assertEquals(response.getBody().getSize(), coreOutput.getSize());
                assertEquals(response.getBody().getTotalElements(), coreOutput.getTotalElements());
                assertEquals(response.getBody().getNumberOfElements(), coreOutput.getNumberOfElements());

                AtomicInteger index = new AtomicInteger();
                response.getBody().getContent().forEach((planetResponse) -> {
                    assertEquals(planetResponse.getName(), coreOutput.getContent().get(index.get()).getName());
                    assertEquals(planetResponse.getGround(), coreOutput.getContent().get(index.get()).getGround());
                    assertEquals(planetResponse.getClimate(), coreOutput.getContent().get(index.get()).getClimate());
                    assertEquals(planetResponse.getFilmAppearances(), coreOutput.getContent().get(index.get()).getFilmAppearances());

                    index.getAndIncrement();
                });

            } finally {
                clearMocks();
            }
        }

        @Test
        public void findAll_shouldFindAllWhenPageAndPerPageIsNotSetAndNameFilterIsSet() {
            mockFields();
            try {
                var coreOutput = PlanetMock.listByName(0, 50, "planetName");
                doReturn(coreOutput).when(planetPortInMock).findAll(any());

                var url = UriComponentsBuilder
                        .fromUriString("/star-wars/v1/planets")
                        .queryParam("name", "planetName")
                        .build()
                        .toUriString();

                var response = restTemplate.exchange(
                        url,
                        GET,
                        new HttpEntity<>(new HttpHeaders()),
                        new ParameterizedTypeReference<HelperPage<ReadPlanetDto>>() {});

                verify(planetPortInMock, times(1)).findAll(filtersArgumentCaptorCaptor.capture());

                var capturedFilter = filtersArgumentCaptorCaptor.getValue();

                assertEquals(capturedFilter.getPage(), 0);
                assertEquals(capturedFilter.getPerPage(), 50);
                assertEquals(capturedFilter.getName(), "planetName");

                assertEquals(response.getStatusCode(), OK);
                assertNotNull(response.getBody());
                assertEquals(response.getBody().getNumber(), coreOutput.getNumber());
                assertEquals(response.getBody().getSize(), coreOutput.getSize());
                assertEquals(response.getBody().getTotalElements(), coreOutput.getTotalElements());
                assertEquals(response.getBody().getNumberOfElements(), coreOutput.getNumberOfElements());

                AtomicInteger index = new AtomicInteger();
                response.getBody().getContent().forEach((planetResponse) -> {
                    assertEquals(planetResponse.getName(), "planetName");
                    assertEquals(planetResponse.getGround(), coreOutput.getContent().get(index.get()).getGround());
                    assertEquals(planetResponse.getClimate(), coreOutput.getContent().get(index.get()).getClimate());
                    assertEquals(planetResponse.getFilmAppearances(), coreOutput.getContent().get(index.get()).getFilmAppearances());

                    index.getAndIncrement();
                });

            } finally {
                clearMocks();
            }
        }

        @Test
        public void findAll_shouldFindAllWhenPageAndPerPageAndNameFilterIsSet() {
            mockFields();
            try {
                var coreOutput = PlanetMock.listByName(1, 2, "planetName");

                doReturn(coreOutput).when(planetPortInMock).findAll(any());

                var url = UriComponentsBuilder
                        .fromUriString("/star-wars/v1/planets")
                        .queryParam("page", 1)
                        .queryParam("perPage", 2)
                        .queryParam("name", "planetName")
                        .build()
                        .toUriString();

                var response = restTemplate.exchange(
                        url,
                        GET,
                        new HttpEntity<>(new HttpHeaders()),
                        new ParameterizedTypeReference<HelperPage<ReadPlanetDto>>() {});

                verify(planetPortInMock, times(1)).findAll(filtersArgumentCaptorCaptor.capture());

                var capturedFilter = filtersArgumentCaptorCaptor.getValue();

                assertEquals(capturedFilter.getPage(), 1);
                assertEquals(capturedFilter.getPerPage(), 2);
                assertEquals(capturedFilter.getName(), "planetName");

                assertEquals(response.getStatusCode(), OK);
                assertNotNull(response.getBody());
                assertEquals(response.getBody().getNumber(), coreOutput.getNumber());
                assertEquals(response.getBody().getSize(), coreOutput.getSize());
                assertEquals(response.getBody().getTotalElements(), coreOutput.getTotalElements());
                assertEquals(response.getBody().getNumberOfElements(), coreOutput.getNumberOfElements());

                AtomicInteger index = new AtomicInteger();
                response.getBody().getContent().forEach((planetResponse) -> {
                    assertEquals(planetResponse.getName(), "planetName");
                    assertEquals(planetResponse.getGround(), coreOutput.getContent().get(index.get()).getGround());
                    assertEquals(planetResponse.getClimate(), coreOutput.getContent().get(index.get()).getClimate());
                    assertEquals(planetResponse.getFilmAppearances(), coreOutput.getContent().get(index.get()).getFilmAppearances());


                    index.getAndIncrement();
                });

            } finally {
                clearMocks();
            }
        }
    }

    public void clearMocks() {
        ReflectionTestUtils.setField(planetAdapterIn, "planetPortIn", planetPortIn);
    }
}