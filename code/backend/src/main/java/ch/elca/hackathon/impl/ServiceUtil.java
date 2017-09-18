package ch.elca.hackathon.impl;

import ch.elca.hackathon.exception.BackendStaleObjectException;
import ch.elca.hackathon.impl.model.IdentifiableEntity;

/**
 * The {@link ServiceUtil} contains service-specific utility methods.
 */
public class ServiceUtil {

    /**
     * Hidden default constructor.
     */
    private ServiceUtil() {
        // nop
    }

    /**
     * Asserts that the id and version of the {@link IdentifiableEntity} match.
     *
     * @param clientObject the {@link IdentifiableEntity} provided by the client
     * @param serverObject the {@link IdentifiableEntity} provided by the server
     */
    public static final void assertVersion(IdentifiableEntity clientObject, IdentifiableEntity serverObject) throws BackendStaleObjectException {
        if (clientObject.getId().compareTo(serverObject.getId()) != 0) {
            throw new IllegalArgumentException();
        }

        if (clientObject.getVersion().compareTo(serverObject.getVersion()) != 0) {
            throw new BackendStaleObjectException(clientObject);
        }
    }
}
