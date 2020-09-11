package com.recruitment.challenge.adapter.in;

import com.recruitment.challenge.dto.CreatePlanetDto;
import com.recruitment.challenge.dto.PaginatePlanetFilters;
import com.recruitment.challenge.dto.ReadPlanetDto;
import com.recruitment.challenge.port.in.PlanetPortIn;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<ReadPlanetDto>> findAll(@RequestParam("page") int page,
                                                       @RequestParam("perPage") int perPage,
                                                       @RequestParam("name") String name) {

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

        return ResponseEntity.created(URI.create("/star-wars/v1/planets/" + id)).build();
    }

}
