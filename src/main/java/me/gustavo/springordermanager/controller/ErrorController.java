package me.gustavo.springordermanager.controller;

import me.gustavo.springordermanager.exception.EntityAlreadyExistsException;
import me.gustavo.springordermanager.exception.EntityNotCreatedException;
import me.gustavo.springordermanager.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger("order_processing");

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleEntityAlreadyExistsException(Exception exception) {
        LOGGER.error("An error has occurred: ", exception);

        return new ResponseEntity<>("An error has occurred. Please try again later. If the issue persists, contact support.", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({EntityAlreadyExistsException.class})
    public ResponseEntity<String> handleEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
        LOGGER.error("An error has occurred: ", exception);

        return new ResponseEntity<>(
                String.format("An %s entity with provided %s value already exists.",
                        exception.getEntityName(), exception.getProperty()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({EntityNotCreatedException.class})
    public ResponseEntity<String> handleEntityNotCreatedException(EntityNotCreatedException exception) {
        LOGGER.error("An error has occurred: ", exception);
        return new ResponseEntity<>(
                String.format("Entity %s could not be created.",
                        exception.getEntityName()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException exception) {
        LOGGER.error("An error has occurred: ", exception);
        return new ResponseEntity<>(
                String.format("Entity %s with given id was not found.",
                        exception.getEntityName()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
