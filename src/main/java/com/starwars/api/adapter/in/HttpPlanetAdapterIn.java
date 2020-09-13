package com.starwars.api.adapter.in;

import com.starwars.api.dto.CreatePlanetDto;
import com.starwars.api.dto.PaginatePlanetFilters;
import com.starwars.api.dto.ReadPlanetDto;
import com.starwars.api.port.in.PlanetPortIn;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/star-wars/v1/planets")
public class HttpPlanetAdapterIn {

    private final PlanetPortIn planetPortIn;

    public HttpPlanetAdapterIn(final PlanetPortIn planetPortIn) {
        this.planetPortIn = planetPortIn;
    }

    @GetMapping
    public ResponseEntity<Page<ReadPlanetDto>> findAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                       @RequestParam(value = "perPage", required = false, defaultValue = "50") int perPage,
                                                       @RequestParam(value = "name", required = false) String name) {

        var response = this.planetPortIn.findAll(new PaginatePlanetFilters(page, perPage, name));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadPlanetDto> findById(@PathVariable("id") String id) {

        var response = this.planetPortIn.findById(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {

        this.planetPortIn.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreatePlanetDto planet) {

        String id = this.planetPortIn.create(planet);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
