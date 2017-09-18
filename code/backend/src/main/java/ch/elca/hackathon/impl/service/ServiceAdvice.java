package ch.elca.hackathon.impl.service;

import ch.elca.hackathon.exception.BackendStaleObjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

/**
 * The {@link ServiceAdvice} provides advice on how to respond in the exception case.
 */
@ControllerAdvice
public class ServiceAdvice {

    /**
     * The class-specific {@link Logger} instance.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAdvice.class);

    private static VndErrors createAndLogVndErrors(Throwable throwable) {
        String uuid = UUID.randomUUID().toString();
        String message = String.valueOf(throwable.getMessage());

        LOGGER.warn(uuid + ": " + message, throwable);

        return new VndErrors(uuid, message);
    }

    /**
     * Transforms a {@link DataAccessException} into the corresponding {@link VndErrors} object.
     * <p>
     * <b>Note:</b> {@link DataAccessException} are thrown upon an error when accessing the data access API (e.g. JDBC)
     * </p>
     *
     * @param exception the {@link DataAccessException} instance
     * @return a {@link VndErrors} object
     */
    @ResponseBody
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public VndErrors databaseAccessExceptionHandler(DataAccessException exception) {
        return createAndLogVndErrors(exception);
    }

    /**
     * Transforms the {@link MethodArgumentNotValidException} into the corresponding {@link VndErrors} object.
     *
     * @param exception the {@link MethodArgumentNotValidException}
     * @return a {@link VndErrors} object
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public VndErrors methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        return createAndLogVndErrors(exception);
    }

    /**
     * Transforms a {@link EmptyResultDataAccessException} into the corresponding {@link VndErrors} object.
     *
     * @param exception the {@link EmptyResultDataAccessException} instance
     * @return a {@link VndErrors} object
     */
    @ResponseBody
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public VndErrors emptyResultDataAccessExceptionHandler(EmptyResultDataAccessException exception) {
        return createAndLogVndErrors(exception);
    }

    /**
     * Transforms a {@link EntityNotFoundException} into the corresponding {@link VndErrors} object.
     *
     * @param exception the {@link EntityNotFoundException} instance
     * @return a {@link VndErrors} object
     */
    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public VndErrors entityNotFoundExceptionHandler(EntityNotFoundException exception) {
        return createAndLogVndErrors(exception);
    }

    /**
     * Transforms a {@link BackendStaleObjectException} into the corresponding {@link VndErrors} object.
     *
     * @param exception the {@link BackendStaleObjectException} instance
     * @return a {@link VndErrors} object
     */
    @ResponseBody
    @ExceptionHandler(BackendStaleObjectException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public VndErrors staleDatabaseRecordExceptionHandler(BackendStaleObjectException exception) {
        return createAndLogVndErrors(exception);
    }

    /**
     * Transforms a {@link ObjectOptimisticLockingFailureException} into the corresponding {@link VndErrors} object.
     *
     * @param exception the {@link ObjectOptimisticLockingFailureException} instance
     * @return a {@link VndErrors} object
     */
    @ResponseBody
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public VndErrors objectOptimisticLockingFailureExceptionHandler(ObjectOptimisticLockingFailureException exception) {
        return createAndLogVndErrors(exception);
    }

    /**
     * Transforms a generic {@link Throwable} into the corresponding {@link VndErrors} object.
     *
     * @param throwable the {@link Throwable} instance
     * @return a {@link VndErrors} object
     */
    @ResponseBody
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public VndErrors throwableHandler(Throwable throwable) {
        return createAndLogVndErrors(throwable);
    }

}
