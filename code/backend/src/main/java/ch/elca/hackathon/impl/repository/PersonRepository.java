package ch.elca.hackathon.impl.repository;

import ch.elca.hackathon.impl.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Provides access to the {@link Person}.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

    Page<Person> findByNameContaining(@Param("name") String name, Pageable p);

}
