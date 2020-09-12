package com.starwars.api.port.out;

import com.starwars.api.commons.exceptions.AdapterOutboundException;

public interface StarWarsApiPortOut {

    int getFilmAppearancesByPlanet(String planetName) throws AdapterOutboundException;

}
