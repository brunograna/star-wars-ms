package com.starwars.api.commons;

import javax.validation.*;
import java.util.Set;

public class SelfValidation<T> {

    private final Validator validator;

    public SelfValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public void validate() throws ConstraintViolationException {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
