package ch.elca.hackathon.impl.service;

import ch.elca.hackathon.impl.model.Person;
import ch.elca.hackathon.impl.repository.PersonRepository;
import com.jayway.restassured.http.ContentType;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ch.elca.hackathon.impl.service.PersonController.PATH;
import static com.jayway.restassured.RestAssured.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONCompareMode.NON_EXTENSIBLE;


/**
 * Tests the {@link PersonController} REST interface.
 */
public class PersonControllerIT extends AbstractBackendIT {

    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE = 5;

    private static final String PERSON_NAME = "PERSON_NAME";

    private static final String REFERENCE_FILE_NAME_GET_REQUEST = "reference_get.json";
    private static final String REFERENCE_FILE_NAME_PUT_REQUEST = "reference_put.json";

    @MockBean
    private PersonRepository personRepository;

    private static Person createPerson() {
        Person person = new Person();

        person.setId(1L);
        person.setVersion(1L);
        person.setName(PERSON_NAME);

        return person;
    }

    @Test
    public void testGetPersonsByNameContaining() throws Exception {
        PageRequest pageRequest = new PageRequest(PAGE_NUMBER, PAGE_SIZE);
        Page<Person> page = new PageBuilder<Person>().pageRequest(pageRequest).elements(Collections.singletonList(createPerson())).totalElements(1).build();

        when(personRepository.findAll(any(Pageable.class))).thenReturn(page);

        // @formatter:off
        String responseBody = given()
            .spec(buildRequestSpecification(PATH))
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value())
            .extract().asString();
        // @formatter:on

        JSONAssert.assertEquals(IOUtils.toString(getClass().getResourceAsStream(REFERENCE_FILE_NAME_GET_REQUEST), StandardCharsets.UTF_8),
            responseBody, NON_EXTENSIBLE);
    }

    @Test
    public void testPut() throws Exception {
        Person person = createPerson();
        Person updatedPerson = createPerson();
        updatedPerson.setVersion(updatedPerson.getId() + 1);

        when(personRepository.getOne(person.getId())).thenReturn(person);
        when(personRepository.saveAndFlush(person)).thenReturn(updatedPerson);

        // @formatter:off
        String responseBody = given()
            .spec(buildRequestSpecification(PATH))
            .contentType(ContentType.JSON)
            .body(person)
        .when()
            .put(String.valueOf(person.getId()))
        .then()
            .statusCode(HttpStatus.OK.value())
            .extract().asString();
        // @formatter:on

        JSONAssert.assertEquals(IOUtils.toString(getClass().getResourceAsStream(REFERENCE_FILE_NAME_PUT_REQUEST), StandardCharsets.UTF_8),
            responseBody, NON_EXTENSIBLE);
    }

    /**
     * Builder to support the creation of {@link Page} objects.
     *
     * @param <T> the {@link Page} object type
     */
    private static class PageBuilder<T> {

        private List<T> elements = new ArrayList<>();
        private Pageable pageRequest;
        private int totalElements;

        public PageBuilder() {
        }

        public PageBuilder<T> elements(List<T> elements) {
            this.elements = elements;
            return this;
        }

        public PageBuilder<T> pageRequest(Pageable pageRequest) {
            this.pageRequest = pageRequest;
            return this;
        }

        public PageBuilder<T> totalElements(int totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public Page<T> build() {
            return new PageImpl<T>(elements, pageRequest, totalElements);
        }
    }
}
