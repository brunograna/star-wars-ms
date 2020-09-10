package com.recruitment.challenge.commons.exceptions;

import java.util.ArrayList;
import java.util.List;

public abstract class DomainException extends RuntimeException {

    private static final long serialVersionUID = -1L;

    public DomainException() {

    }

    public DomainException(String message) {
        super(message);
    }

}
