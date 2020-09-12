package com.starwars.api.core;

import com.starwars.api.commons.exceptions.NotFoundException;
import com.starwars.api.domain.Planet;
import com.starwars.api.dto.CreatePlanetDto;
import com.starwars.api.dto.PaginatePlanetFilters;
import com.starwars.api.mocks.PlanetMock;
import com.starwars.api.port.out.PlanetDatabasePortOut;
import com.starwars.api.port.out.StarWarsApiPortOut;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Planet Core")
public class PlanetCoreTest {

    @Autowired
    PlanetCore planetCore;

    @Autowired
    PlanetDatabasePortOut database;
    @Mock
    PlanetDatabasePortOut databaseMock;
    @Autowired
    StarWarsApiPortOut starWarsApi;
    @Mock
    StarWarsApiPortOut starWarsApiMock;

    private void mockFields() {
        ReflectionTestUtils.setField(planetCore, "database", databaseMock);
        ReflectionTestUtils.setField(planetCore, "starWarsApi", starWarsApiMock);
    }

    @Nested
    @DisplayName("Operation - FindById")
    class FindById {

        @Test
        public void findById_shouldFindWhenExistsOnDatabase() {
            mockFields();
            try {
                var input = PlanetMock.success();

                doReturn(Optional.of(input)).when(databaseMock).findById(anyString());
                doReturn(3).when(starWarsApiMock).getFilmAppearancesByPlanet(anyString());

                var output = planetCore.findById("12345");

                verify(databaseMock, times(1)).findById(anyString());

                assertAll(() -> {
                    assertEquals(input.getClimate(), output.getClimate());
                    assertEquals(input.getGround(), output.getGround());
                    assertEquals(input.getName(), output.getName());
                    assertEquals(3, output.getFilmAppearances());
                });

            } finally {
                clearMocks();
            }
        }

        @Test
        public void findById_shouldThrowNotFoundWhenNotExistsOnDatabase() {
            mockFields();
            try {
                assertThrows(NotFoundException.class, () -> {

                    doReturn(Optional.empty()).when(databaseMock).findById(anyString());

                    planetCore.findById("12345");
                });
            } finally {
                clearMocks();
            }
        }

        @Test
        public void findById_shouldThrowExceptionWhenParamIsNull() {
            mockFields();
            try {
                Throwable exception = assertThrows(ConstraintViolationException.class, () -> planetCore.findById(null));
            } finally {
                clearMocks();
            }
        }
    }

    @Nested
    @DisplayName("Operation - DeleteById")
    class DeleteById {
        @Test
        public void deleteById_shouldDeleteOnDatabase() {
            mockFields();
            try {

                var input = PlanetMock.success();

                doReturn(Optional.of(input)).when(databaseMock).findById(anyString());
                doNothing().when(databaseMock).deleteById(anyString());

                planetCore.deleteById("12345");

                verify(databaseMock, times(1)).deleteById(anyString());
            } finally {
                clearMocks();
            }
        }

        @Test
        public void deleteById_shouldThrowNotFoundWhenNotExistsOnDatabase() {
            mockFields();
            try {
                assertThrows(NotFoundException.class, () -> {

                    doReturn(Optional.empty()).when(databaseMock).findById(anyString());

                    planetCore.deleteById("12345");
                });
            } finally {
                clearMocks();
            }
        }

        @Test
        public void deleteById_shouldThrowExceptionWhenParamIsNull() {
            mockFields();
            try {
                Throwable exception = assertThrows(ConstraintViolationException.class,
                        () -> planetCore.deleteById(null));
            } finally {
                clearMocks();
            }
        }
    }

    @Nested
    @DisplayName("Operation - Create")
    class Create {

        @Captor
        ArgumentCaptor<Planet> planetArgumentCaptor;

        @Test
        public void create_shouldCreateOnDatabase() {
            mockFields();
            try {

                var databaseOutput = PlanetMock.success();
                databaseOutput.setId("12345");

                doReturn(databaseOutput).when(databaseMock).create(any());

                var input = new CreatePlanetDto("name", "climate", "ground");

                var id = planetCore.create(input);

                assertEquals(id, "12345");

                verify(databaseMock, times(1)).create(planetArgumentCaptor.capture());

                var outputCaptor = planetArgumentCaptor.getValue();

                assertEquals(outputCaptor.getName(), input.getName());
                assertEquals(outputCaptor.getGround(), input.getGround());
                assertEquals(outputCaptor.getClimate(), input.getClimate());
            } finally {
                clearMocks();
            }
        }

        @Test
        public void create_shouldThrowExceptionWhenNameIsBlank() {
            mockFields();
            try {

                Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
                    var databaseOutput = PlanetMock.success();
                    databaseOutput.setId("12345");

                    doReturn(databaseOutput).when(databaseMock).create(any());

                    var input = new CreatePlanetDto("", "climate", "ground");

                    planetCore.create(input);
                });

                assertTrue(exception.getMessage().contains("name"));
                assertFalse(exception.getMessage().contains("climate"));
                assertFalse(exception.getMessage().contains("ground"));
            } finally {
                clearMocks();
            }
        }
        @Test
        public void create_shouldThrowExceptionWhenClimateIsBlank() {
            mockFields();
            try {

                Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
                    var databaseOutput = PlanetMock.success();
                    databaseOutput.setId("12345");

                    doReturn(databaseOutput).when(databaseMock).create(any());

                    var input = new CreatePlanetDto("name", "", "ground");

                    planetCore.create(input);
                });

                assertFalse(exception.getMessage().contains("name"));
                assertTrue(exception.getMessage().contains("climate"));
                assertFalse(exception.getMessage().contains("ground"));
            } finally {
                clearMocks();
            }
        }
        @Test
        public void create_shouldThrowExceptionWhenGroundIsBlank() {
            mockFields();
            try {

                Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
                    var databaseOutput = PlanetMock.success();
                    databaseOutput.setId("12345");

                    doReturn(databaseOutput).when(databaseMock).create(any());

                    var input = new CreatePlanetDto("name", "climate", "");

                    planetCore.create(input);
                });

                assertFalse(exception.getMessage().contains("name"));
                assertFalse(exception.getMessage().contains("climate"));
                assertTrue(exception.getMessage().contains("ground"));
            } finally {
                clearMocks();
            }
        }

        @Test
        public void create_shouldThrowExceptionWhenParamIsNull() {
            mockFields();
            try {
                assertThrows(ConstraintViolationException.class, () -> {
                    planetCore.create(null);
                });
            } finally {
                clearMocks();
            }
        }

    }

    @Nested
    @DisplayName("Operation - FindAll")
    class FindAll {

        @Captor
        ArgumentCaptor<PaginatePlanetFilters> filtersArgumentCaptor;

        @Test
        public void findAll_shouldFindAll() {
            mockFields();
            try {

                var databaseOutput = PlanetMock.success();
                databaseOutput.setId("12345");

                doReturn(5).when(starWarsApiMock).getFilmAppearancesByPlanet(anyString());

                var pageMock = new PageImpl<>(Collections.singletonList(databaseOutput), PageRequest.of(0, 5), 1);

                doReturn(pageMock).when(databaseMock).findAll(any());

                var input = new PaginatePlanetFilters(0, 5);

                var output = planetCore.findAll(input);

                verify(databaseMock, times(1)).findAll(filtersArgumentCaptor.capture());

                var outputCaptor = filtersArgumentCaptor.getValue();

                assertAll(() -> {
                    assertEquals(output.getTotalElements(), 1);
                    assertEquals(output.getSize(), 5);
                    assertEquals(output.getNumber(), 0);

                    assertEquals(outputCaptor.getPage(), input.getPage());
                    assertEquals(outputCaptor.getPerPage(), input.getPerPage());
                    assertEquals(outputCaptor.getName(), input.getName());

                    output.getContent().forEach((planet) -> {
                        assertEquals(planet.getClimate(), databaseOutput.getClimate());
                        assertEquals(planet.getGround(), databaseOutput.getGround());
                        assertEquals(planet.getName(), databaseOutput.getName());
                        assertEquals(planet.getFilmAppearances(), 5);
                    });
                });
            } finally {
                clearMocks();
            }
        }

        @Test
        public void findAll_shouldThrowExceptionWhenPageIsNegative() {
            mockFields();
            try {

                Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
                    var databaseOutput = PlanetMock.success();
                    databaseOutput.setId("12345");

                    var pageMock = new PageImpl<>(Collections.singletonList(databaseOutput), PageRequest.of(0, 5), 1);

                    doReturn(pageMock).when(databaseMock).findAll(any());

                    var input = new PaginatePlanetFilters(-2, 5);

                    var output = planetCore.findAll(input);
                });

                assertTrue(exception.getMessage().contains("page"));
                assertFalse(exception.getMessage().contains("perPage"));
                assertFalse(exception.getMessage().contains("name"));
            } finally {
                clearMocks();
            }
        }
        @Test
        public void findAll_shouldThrowExceptionWhenPerPageIsNegative() {
            mockFields();
            try {

                Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
                    var databaseOutput = PlanetMock.success();
                    databaseOutput.setId("12345");

                    var pageMock = new PageImpl<>(
                            Collections.singletonList(databaseOutput),
                            PageRequest.of(0, 5),
                            1);

                    doReturn(pageMock).when(databaseMock).findAll(any());

                    var input = new PaginatePlanetFilters(0, -5);

                    var output = planetCore.findAll(input);
                });

                assertTrue(exception.getMessage().contains("perPage"));
                assertFalse(exception.getMessage().contains("page"));
                assertFalse(exception.getMessage().contains("name"));
            } finally {
                clearMocks();
            }
        }
        @Test
        public void findAll_shouldThrowExceptionWhenParamIsNull() {
            mockFields();
            try {
                Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
                    var output = planetCore.findAll(null);
                });
            } finally {
                clearMocks();
            }
        }

    }


    private void clearMocks() {
        ReflectionTestUtils.setField(planetCore, "database", database);
        ReflectionTestUtils.setField(planetCore, "starWarsApi", starWarsApi);
    }
}