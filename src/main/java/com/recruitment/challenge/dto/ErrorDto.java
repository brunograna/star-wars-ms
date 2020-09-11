package com.recruitment.challenge.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ErrorDto implements Serializable {

    private static final long serialVersionUID = -2279690562401212479L;

    private int statusCode;

    private String message;

    public ErrorDto(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ErrorDto() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
