package ch.elca.hackathon.impl.model;

/**
 * Base entity for all {@link IdentifiableEntity}.
 */
public interface IdentifiableEntity {

    Long getId();

    Long getVersion();

}
