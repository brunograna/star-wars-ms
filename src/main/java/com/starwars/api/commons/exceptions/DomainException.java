package com.starwars.api.commons.exceptions;

public abstract class DomainException extends RuntimeException {

    private static final long serialVersionUID = -1L;

    public DomainException() {

    }

    public DomainException(String message) {
        super(message);
    }

}
