package com.recruitment.challenge.commons.exceptions;

import com.mongodb.MongoException;
import com.recruitment.challenge.dto.ErrorDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@ControllerAdvice
public class ErrorHandler {

    private final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    //// ERRO 400

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody
    ErrorDto handleBadRequest(HttpServletRequest req, Exception ex) {
        logger.warn("handle-bad-request; exception; system; exception=\"{}\";", getStackTrace(ex));
        String errorMessage = this.buildErrorItems(((MethodArgumentNotValidException) ex).getBindingResult());
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public @ResponseBody
    ErrorDto handleBadRequestMissingServletRequestParameterException(HttpServletRequest req, Exception ex) {
        logger.warn("handle-bad-request-missing-servlet-request-parameter-exception; exception; system; exception=\"{}\";", getStackTrace(ex));
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public @ResponseBody
    ErrorDto handleConstraintViolationException(HttpServletRequest req, Exception ex) {
        logger.warn("handle-constraint-violation-exception; exception; system; exception=\"{}\";", getStackTrace(ex));

        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), this.buildError(((ConstraintViolationException) ex).getConstraintViolations()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public @ResponseBody
    ErrorDto handleIlegalArgument(HttpServletRequest req, Exception ex) {
        logger.warn("handle-ilegal-argument; exception; system; exception=\"{}\";", getStackTrace(ex));
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody
    ResponseEntity<?> handleNotFound(HttpServletRequest req, Exception ex) {
        logger.warn("handle-not-found; exception; system; exception=\"{}\";", getStackTrace(ex));
        return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseBody
    public ErrorDto handleMethodArgumentTypeMismatchException(HttpServletRequest req, Exception e) {
        logger.warn("handle-method-argument-type-mismatch-exception; exception; system; exception=\"{}\"", getStackTrace(e));
        String errorMessage = this.buildErrorMessage((MethodArgumentTypeMismatchException) e);
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UnsatisfiedServletRequestParameterException.class})
    @ResponseBody
    public ErrorDto handleUnsatisfiedServletRequestParameterException(HttpServletRequest req, Exception e) {
        logger.warn("handle-unsatisfied-servlet-request-parameter-exception; exception; system; exception=\"{}\"", getStackTrace(e));
        String errorMessage = buildErrorMessage((UnsatisfiedServletRequestParameterException) e);
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(),
                errorMessage);
    }


    ///// ERRO 500

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public @ResponseBody
    ResponseEntity<?> handleNullPointer(HttpServletRequest req, Exception ex) {
        logger.error("handle-null-pointer; exception; system; exception=\"{}\";", getStackTrace(ex));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MongoException.class)
    public @ResponseBody
    ResponseEntity<?> handleMongoExceptionException(HttpServletRequest req, Exception ex) {
        logger.error("handle-mongo-exception; exception; system; exception=\"{}\";", getStackTrace(ex));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    private String buildErrorItems(BindingResult bindingResult) {
        if (bindingResult.getFieldErrors().isEmpty()) {
            return "";
        }

        return bindingResult.getFieldErrors().get(0).getField()
                + " " + bindingResult.getFieldErrors().stream().findFirst().get().getDefaultMessage();
    }

    private String buildErrorMessage(MethodArgumentTypeMismatchException e) {
        return StringUtils.join(e.getName(), " ", "está no formato inválido");
    }

    private String buildErrorMessage(UnsatisfiedServletRequestParameterException e) {
        return StringUtils.join(e.getActualParams().entrySet().iterator().next().getKey(), " ", "não pode estar em branco");
    }

    private String buildError(final Set<ConstraintViolation<?>> errors){
        String queryParam ="";
        String errorMessage ="";
        for (ConstraintViolation<?> constraintViolation : errors) {
            String queryParamPath = constraintViolation.getPropertyPath().toString();
            logger.debug("queryParamPath = {}", queryParamPath);
            queryParam = queryParamPath.contains(".") ?
                    queryParamPath.substring(queryParamPath.indexOf(".") + 1) :
                    queryParamPath;
            errorMessage = constraintViolation.getMessage();
        }

        return queryParam + " " + errorMessage;
    }
}