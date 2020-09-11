package com.recruitment.challenge.adapter.in;

import com.recruitment.challenge.ChallengeApplication;
import com.recruitment.challenge.commons.exceptions.NotFoundException;
import com.recruitment.challenge.dto.CreatePlanetDto;
import com.recruitment.challenge.dto.ErrorDto;
import com.recruitment.challenge.dto.ReadPlanetDto;
import com.recruitment.challenge.port.in.PlanetPortIn;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(classes = ChallengeApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("HTTP Planet API")
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
                assertEquals("/star-wars/v1/planets/12345", response.getHeaders().getLocation().toString());
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
                var input = new ReadPlanetDto("name", "climate", "ground", null);

                doReturn(input).when(planetPortInMock).findById(any());

                var response = restTemplate.exchange(
                        "/star-wars/v1/planets/12345",
                        GET,
                        new HttpEntity<>(null),
                        ReadPlanetDto.class);

                assertEquals(response.getStatusCode(), OK);
                assertNotNull(response.getBody());
                assertEquals(response.getBody().getClimate(), input.getClimate());
                assertEquals(response.getBody().getGround(), input.getGround());
                assertEquals(response.getBody().getName(), input.getName());
                assertEquals(response.getBody().getFilms(), input.getFilms());

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
                        new HttpEntity<>(null),
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
                        new HttpEntity<>(null),
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
                        new HttpEntity<>(null),
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

    }

    public void clearMocks() {
        ReflectionTestUtils.setField(planetAdapterIn, "planetPortIn", planetPortIn);
    }
}