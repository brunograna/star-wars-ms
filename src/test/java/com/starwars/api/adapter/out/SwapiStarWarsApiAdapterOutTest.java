package com.starwars.api.adapter.out;

import com.starwars.api.StarWarsMsApplication;
import com.starwars.api.commons.exceptions.AdapterOutboundException;
import com.starwars.api.dto.SwapiPlanetDto;
import com.starwars.api.dto.SwapiResponseDto;
import com.starwars.api.infra.MongoPlanetRepository;
import com.starwars.api.mocks.PlanetMock;
import com.starwars.api.mocks.SwapiApiMock;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@SpringBootTest
@DisplayName("Swapi Star Wars Api Adapter")
public class SwapiStarWarsApiAdapterOutTest {

    @Autowired
    private MongoPlanetRepository repository;

    @Autowired
    private SwapiStarWarsApiAdapterOut swapiStarWarsApiAdapterOut;

    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    @Mock
    private RestTemplate restTemplateMock;

    public void mockFields() {
        ReflectionTestUtils.setField(swapiStarWarsApiAdapterOut, "restTemplate", restTemplateMock);
    }

    @Nested
    @DisplayName("Operation - Get Film Appearances By Planet")
    class Create {

        @Captor
        ArgumentCaptor<String> uriCaptor;

        @Test
        void getFilmAppearancesByPlanet_shouldFindWithSuccess() {
            mockFields();
            try {
                var input = SwapiApiMock.success();

                when(restTemplateMock.exchange(
                        anyString(),
                        eq(HttpMethod.GET),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.<ParameterizedTypeReference<SwapiResponseDto<SwapiPlanetDto>>>any())
                ).thenReturn(new ResponseEntity<>(input, OK));

                var output = swapiStarWarsApiAdapterOut.getFilmAppearancesByPlanet("planetName");

                verify(restTemplateMock, times(1)).exchange(
                        uriCaptor.capture(),
                        eq(HttpMethod.GET),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.<ParameterizedTypeReference<SwapiResponseDto<SwapiPlanetDto>>>any());

                var uri = uriCaptor.getValue();

                assertAll(() -> {
                    assertTrue(uri.contains("search=planetName"));
                    assertEquals(output, input.getResults().get(0).getFilms().size());
                });
            } finally {
                clearMock();
            }
        }

        @Test
        void getFilmAppearancesByPlanet_shouldThrowOutboundExceptionWhenErrorOccurs() {
            mockFields();
            try {
                assertThrows(AdapterOutboundException.class, () -> {

                    when(restTemplateMock.exchange(
                            anyString(),
                            eq(HttpMethod.GET),
                            ArgumentMatchers.any(),
                            ArgumentMatchers.<ParameterizedTypeReference<SwapiResponseDto<SwapiPlanetDto>>>any())
                    ).thenThrow(new HttpServerErrorException(SERVICE_UNAVAILABLE));

                    swapiStarWarsApiAdapterOut.getFilmAppearancesByPlanet("planetName");
                });
            } finally {
                clearMock();
            }
        }

        @Test
        void getFilmAppearancesByPlanet_shouldThrowOutboundExceptionWhenBodyIsEmpty() {
            mockFields();
            try {
                assertThrows(AdapterOutboundException.class, () -> {

                    when(restTemplateMock.exchange(
                            anyString(),
                            eq(HttpMethod.GET),
                            ArgumentMatchers.any(),
                            ArgumentMatchers.<ParameterizedTypeReference<SwapiResponseDto<SwapiPlanetDto>>>any())
                    ).thenReturn(new ResponseEntity<>(null, OK));

                    swapiStarWarsApiAdapterOut.getFilmAppearancesByPlanet("planetName");
                });
            } finally {
                clearMock();
            }
        }

        @Test
        void getFilmAppearancesByPlanet_shouldThrowOutboundExceptionWhenStatusIsNotOk() {
            mockFields();
            try {
                assertThrows(AdapterOutboundException.class, () -> {

                    when(restTemplateMock.exchange(
                            anyString(),
                            eq(HttpMethod.GET),
                            ArgumentMatchers.any(),
                            ArgumentMatchers.<ParameterizedTypeReference<SwapiResponseDto<SwapiPlanetDto>>>any())
                    ).thenReturn(new ResponseEntity<>(null, HttpStatus.MOVED_PERMANENTLY));

                    swapiStarWarsApiAdapterOut.getFilmAppearancesByPlanet("planetName");
                });
            } finally {
                clearMock();
            }
        }
    }

    public void clearMock() {
        ReflectionTestUtils.setField(swapiStarWarsApiAdapterOut, "restTemplate", restTemplate);
    }
}