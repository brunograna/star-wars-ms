package com.recruitment.challenge.core;

import com.recruitment.challenge.commons.exceptions.NotFoundException;
import com.recruitment.challenge.domain.Planet;
import com.recruitment.challenge.dto.ReadPlanetDto;
import com.recruitment.challenge.mocks.PlanetMock;
import com.recruitment.challenge.port.out.PlanetDatabasePortOut;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class PlanetCoreTest {

    @Autowired
    PlanetCore planetCore;

    @Autowired
    PlanetDatabasePortOut database;
    @Mock
    PlanetDatabasePortOut databaseMock;

    private void mockFields() {
        ReflectionTestUtils.setField(planetCore, "database", databaseMock);
    }

    @Test
    public void findById_shouldFindWhenExistsOnDatabase() {
        mockFields();
        try {

            var input = PlanetMock.success();

            doReturn(input).when(databaseMock.findById(anyString()));

            var output = this.planetCore.findById("12345");

            assertEquals(input.getClimate(), output.getClimate());
            assertEquals(input.getGround(), output.getGround());
            assertEquals(input.getName(), output.getName());

            int index = 0;
            output.getFilms().forEach((filmOutput) -> {
                assertEquals(input.getFilms().get(index).getDirector(), filmOutput.getDirector());
                assertEquals(input.getFilms().get(index).getOpeningCrawl(), filmOutput.getOpeningCrawl());
                assertEquals(input.getFilms().get(index).getTitle(), filmOutput.getTitle());
            });

        } finally {
            clearMocks();
        }
    }

    private void clearMocks() {
        ReflectionTestUtils.setField(planetCore, "database", database);
    }
}