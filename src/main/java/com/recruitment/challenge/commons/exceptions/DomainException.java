package com.recruitment.challenge.commons.exceptions;

import java.util.ArrayList;
import java.util.List;

public abstract class DomainException extends RuntimeException {

    private static final long serialVersionUID = -1L;

    private List<ErrorDetailDto> details;

    public DomainException() {

    }

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, String messageTemplate, String... values) {
        super(message);

        String resultMessageTemplate = null;

        try {
            details = new ArrayList<ErrorDetailDto>();
        } catch (Exception e) {
            if (values != null && values.length > 0)
                resultMessageTemplate = values[0];
        } finally {
            details.add(new ErrorDetailDto(message, resultMessageTemplate));
        }

    }

    public DomainException(String message, List<ErrorDetailDto> details) {
        super(message);
        this.details = details;
    }

    public DomainException(List<ErrorDetailDto> details) {
        this.details = details;
    }

    public List<ErrorDetailDto> getDetails() {
        return details;
    }

}
