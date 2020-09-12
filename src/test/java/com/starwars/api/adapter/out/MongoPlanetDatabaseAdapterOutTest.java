package com.starwars.api.adapter.out;

import com.starwars.api.StarWarsMsApplication;
import com.starwars.api.domain.Planet;
import com.starwars.api.dto.PaginatePlanetFilters;
import com.starwars.api.infra.MongoPlanetRepository;
import com.starwars.api.mocks.PlanetMock;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = StarWarsMsApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Mongo Database Adapter")
class MongoPlanetDatabaseAdapterOutTest {

    @Autowired
    private MongoPlanetRepository repository;

    @Autowired
    private MongoPlanetDatabaseAdapterOut mongoPlanetDatabaseAdapterOut;
    @Mock
    private MongoPlanetDatabaseAdapterOut mongoPlanetDatabaseAdapterOutMock;

    private Planet planetOne;
    private Planet planetTwo;
    private Planet planetThree;

    @BeforeEach
    public void startup() {
        this.repository.deleteAll();
        planetOne = this.repository.save(PlanetMock.success());
        planetTwo = this.repository.save(PlanetMock.success());
        planetThree = this.repository.save(PlanetMock.success());
    }

    @Nested
    @DisplayName("Operation - Create")
    class Create {


        @Test
        public void create_shouldCreateWithSuccess() {
            var input = new Planet()
                            .setName("name")
                            .setGround("ground")
                            .setClimate("climate");

            var planetCreated = mongoPlanetDatabaseAdapterOut.create(input);

            var output = repository.findById(planetCreated.getId());

            assertAll(() -> {
                assertTrue(output.isPresent());
                assertNotNull(output.get().getId());
                assertNotNull(output.get().getCreatedAt());
                assertNull(output.get().getUpdatedAt());
                assertEquals(input.getName(), output.get().getName());
                assertEquals(input.getGround(), output.get().getGround());
                assertEquals(input.getClimate(), output.get().getClimate());
            });
        }

    }

    @Nested
    @DisplayName("Operation - FindById")
    class FindById {

        @Test
        public void findById_shouldFindWithSuccess() {
            var input = planetOne;

            var output = mongoPlanetDatabaseAdapterOut.findById(input.getId());

            assertAll(() -> {
                assertTrue(output.isPresent());
                assertNotNull(output.get().getId());
                assertEquals(input.getName(), output.get().getName());
                assertEquals(input.getGround(), output.get().getGround());
                assertEquals(input.getClimate(), output.get().getClimate());
            });
        }

    }

    @Nested
    @DisplayName("Operation - DeleteById")
    class DeleteById {

        @Test
        public void deleteById_shouldDeleteWithSuccess() {
            var input = planetOne;

            var output = repository.findById(input.getId());

            assertTrue(output.isPresent());

            mongoPlanetDatabaseAdapterOut.deleteById(input.getId());

            output = repository.findById(input.getId());

            assertTrue(output.isEmpty());
        }

    }

    @Nested
    @DisplayName("Operation - FindAll")
    class FindAll {

        @Test
        public void findAll_shouldFindFirstRegistry() {


            var output = mongoPlanetDatabaseAdapterOut.findAll(new PaginatePlanetFilters(0, 1));
            assertAll(() -> {
                assertEquals(output.getTotalElements(), 3);
                assertEquals(output.getNumber(), 0);
                assertEquals(output.getSize(), 1);
                assertEquals(output.getNumberOfElements(), 1);
                assertEquals(output.getTotalPages(), 3);
                assertEquals(output.getPageable().getPageNumber(), 0);
                assertEquals(output.getPageable().getPageSize(), 1);

                assertEquals(output.getContent().get(0).getName(), planetOne.getName());
                assertEquals(output.getContent().get(0).getGround(), planetOne.getGround());
                assertEquals(output.getContent().get(0).getClimate(), planetOne.getClimate());
            });
        }

        @Test
        public void findAll_shouldFindSecondRegistry() {


            var output = mongoPlanetDatabaseAdapterOut.findAll(new PaginatePlanetFilters(1, 1));
            assertAll(() -> {
                assertEquals(output.getTotalElements(), 3);
                assertEquals(output.getNumber(), 1);
                assertEquals(output.getSize(), 1);
                assertEquals(output.getNumberOfElements(), 1);
                assertEquals(output.getTotalPages(), 3);
                assertEquals(output.getPageable().getPageNumber(), 1);
                assertEquals(output.getPageable().getPageSize(), 1);

                assertEquals(output.getContent().get(0).getName(), planetTwo.getName());
                assertEquals(output.getContent().get(0).getGround(), planetTwo.getGround());
                assertEquals(output.getContent().get(0).getClimate(), planetTwo.getClimate());
            });
        }

        @Test
        public void findAll_shouldFindTwoFirstsRegistries() {


            var output = mongoPlanetDatabaseAdapterOut.findAll(new PaginatePlanetFilters(0, 2));

            assertAll(() -> {
                assertEquals(output.getTotalElements(), 3);
                assertEquals(output.getNumber(), 0);
                assertEquals(output.getSize(), 2);
                assertEquals(output.getNumberOfElements(), 2);
                assertEquals(output.getTotalPages(), 2);
                assertEquals(output.getPageable().getPageNumber(), 0);
                assertEquals(output.getPageable().getPageSize(), 2);

                assertEquals(output.getContent().get(0).getName(), planetOne.getName());
                assertEquals(output.getContent().get(0).getGround(), planetOne.getGround());
                assertEquals(output.getContent().get(0).getClimate(), planetOne.getClimate());

                assertEquals(output.getContent().get(1).getName(), planetTwo.getName());
                assertEquals(output.getContent().get(1).getGround(), planetTwo.getGround());
                assertEquals(output.getContent().get(1).getClimate(), planetTwo.getClimate());
            });
        }

        @Test
        public void findAll_shouldFilterByName() {

            var name = planetOne.getName();
            var output = mongoPlanetDatabaseAdapterOut.findAll(new PaginatePlanetFilters(0, 3, name));

            assertAll(() -> {
                assertEquals(output.getTotalElements(), 1);
                assertEquals(output.getNumber(), 0);
                assertEquals(output.getSize(), 3);
                assertEquals(output.getNumberOfElements(), 1);
                assertEquals(output.getTotalPages(), 1);
                assertEquals(output.getPageable().getPageNumber(), 0);
                assertEquals(output.getPageable().getPageSize(), 3);

                output.getContent().forEach((planetOutput) -> {
                    assertEquals(planetOutput.getName(), name);
                });
            });
        }

    }

    @AfterEach
    public void tearDown() {
        this.repository.deleteAll();
    }

}