package ch.elca.hackathon.exception;

import ch.elca.hackathon.impl.model.IdentifiableEntity;

/**
 * Signals that an entity has been updated in the meantime.
 */
public class BackendStaleObjectException extends BackendRuntimeException {

    /**
     * Creates a new {@link BackendStaleObjectException}.
     *
     * @param entity the stale entity
     */
    public BackendStaleObjectException(IdentifiableEntity entity) {
        super("The entity has been updated in the meantime: " + entity);
    }

}
