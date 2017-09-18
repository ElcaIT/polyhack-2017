package ch.elca.hackathon.impl.service;

import ch.elca.hackathon.exception.BackendStaleObjectException;
import ch.elca.hackathon.impl.ServiceUtil;
import ch.elca.hackathon.impl.model.Person;
import ch.elca.hackathon.impl.repository.PersonRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * The {@link PersonController} provides a REST CRUD interface for {@link Person}.
 * <p>
 * <b>Note:</b> The <code>@Api</code>-prefixed annotations in this file are not really required - they are used to provide a better documentend Swagger
 * specification.
 * </p>
 */
@RestController
@RequestMapping(value = PersonController.PATH)
@Transactional // make sure that interactions with the JPA repository are handled within the same transaction
public class PersonController {

    /**
     * The URL of the <i>REST</i> service.
     * <p>
     * <b>Note:</b> The /api prefix provides a clean separation of the REST service from other resources served by this application and also allows for a
     * simple proxy configuration in the frontend application. (see the <code>proxy.conf</code> file)
     * </p>
     */
    public static final String PATH = "/api/persons";

    /**
     * The class-specific {@link org.slf4j.Logger} instance.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
    private PersonRepository personRepository;

    /**
     * Creates a new {@link PersonController} instance.
     * <p>
     * <b>Note:</b> all dependencies are injected through the constructor to allow for simpler testing.
     * </p>
     *
     * @param personRepository
     */
    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Get {@link Person}s by name (can be partial) or if no name is specified returns all {@link Person}s (with default limit of 20).
     *
     * @param name     the name
     * @param pageable the paging options
     * @return a {@link List} of {@link Person}
     */
    @ApiOperation("get Person by name")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "optional partial name to query for", dataType = "String", paramType = "query", example = "Wol"),
        @ApiImplicitParam(name = "page", value = "what paging page to return (0-based, i.e. 0 is the first page)", dataType = "int", paramType = "query", defaultValue = "0"),
        @ApiImplicitParam(name = "size", value = "what paging size to use (defaults to 20)", dataType = "int", paramType = "query", defaultValue = "20"),
        @ApiImplicitParam(name = "sort", value = "how to sort the returned resources", dataType = "String", paramType = "query", example = "firstname&sort=lastname,asc")
    })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Person[].class)
    })
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Person> getPersonsByNameContaining(@RequestParam(value = "name", required = false) String name,
                                                   @PageableDefault(size = 20) Pageable pageable) {
        LOGGER.debug("Invoking {} GET with name '{}' and pageable '{}'.", PATH, name, pageable);
        if (name != null) {
            return personRepository.findByNameContaining(name, pageable).getContent();
        } else {
            return personRepository.findAll(pageable).getContent();
        }
    }

    /**
     * Retrieves the {@link Person} for the given id.
     *
     * @param id id.
     * @return the {@link Person}
     */
    @ApiOperation("get one Person")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "ID of the entity to get", required = true, dataType = "long", paramType = "path"),
    })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Person.class),
        @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Person getOnePersonById(@PathVariable("id") long id)
        throws IOException {
        LOGGER.debug("Invoking {} GET.", PATH + id);

        Person person = personRepository.getOne(id);

        // make sure that the person is loaded
        person.getName();

        return person;
    }

    /**
     * Create a new {@link Person}.
     *
     * @param person the entity to store
     * @return {@link ResponseEntity} containing the {@link Person}.
     */
    @ApiOperation("create a new Person")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully created", response = Person.class, responseHeaders = {
            @ResponseHeader(name = "Location", response = URI.class)
        }),
        @ApiResponse(code = 400, message = "Wrong parameters")})
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Person createPerson(@Valid @NotNull @RequestBody Person person) throws URISyntaxException {
        LOGGER.debug("Invoking {} POST with person '{}'.", PATH, person);

        if ((person.getId() != null)) {
            // it has an id set, see whether it already exists - in that case it is a mistake (POST is only for creating)
            throw new IllegalArgumentException("Resource already exists with given Id " + person.getId());
        }

        return personRepository.saveAndFlush(person);
    }

    /**
     * Updates a {@link Person}.
     *
     * @param id     the identifier
     * @param person The updated enity
     * @return the updated {@link Person}
     */
    @ApiOperation("update a Person")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "ID of the entity to update", required = true, dataType = "long", paramType = "path"),
    })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully updated", response = Person.class),
        @ApiResponse(code = 400, message = "Wrong parameters"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 409, message = "Stale object")})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Person updatePerson(@PathVariable("id") @NotNull final Long id, @RequestBody @NotNull Person person) throws BackendStaleObjectException {
        LOGGER.debug("Invoking {} PUT with person '{}'.", PATH + id, person);

        if (person.getId() != null && !person.getId().equals(id)) {
            throw new IllegalArgumentException("Id of url is not the same as id of entity");
        }

        // load the entity to get its current version
        Person existing = personRepository.getOne(id);

        // assert that the versions match
        ServiceUtil.assertVersion(person, existing);

        return personRepository.saveAndFlush(person);
    }

    /**
     * Deletes the {@link Person} with the given id.
     *
     * @param id the identifier
     */
    @ApiOperation("delete a Person")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "ID of the person to delete", required = true, dataType = "long", paramType = "path", example = "1"),
    })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully deleted"),
        @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable("id") final long id) {
        LOGGER.debug("Invoking {} DELETE.", PATH + id);

        personRepository.delete(personRepository.getOne(id));
    }

}
