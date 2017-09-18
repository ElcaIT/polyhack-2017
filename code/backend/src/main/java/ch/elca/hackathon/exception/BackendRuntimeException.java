package ch.elca.hackathon.exception;

/**
 * Base {@link RuntimeException} class.
 */
@SuppressWarnings("unused")
public class BackendRuntimeException extends RuntimeException {

    /**
     * Creates a new {@link BackendRuntimeException}.
     *
     * @param message the detail message
     */
    public BackendRuntimeException(String message) {
        super(message);
    }

    /**
     * Creates a new {@link BackendRuntimeException}.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public BackendRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
