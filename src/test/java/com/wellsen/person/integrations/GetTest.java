package com.wellsen.person.integrations;

import com.wellsen.person.models.Person;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GetTest extends IntegrationTest {

  @Test
  void getPersonsAfterPost() throws MalformedURLException {
    Person person = new Person("Wellsen", 39);
    ResponseEntity<Person> postResponse = template.postForEntity(
        new URL("http://localhost:" + port + "/persons").toString(),
        person,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, postResponse.getHeaders().getContentType());
    assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
    assertNotNull(postResponse.getBody());
    assertPerson(person, postResponse.getBody());

    final Long id = postResponse.getBody().getId();

    ResponseEntity<List<Person>> response = template.exchange(
        new URL("http://localhost:" + port + "/persons").toString(),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Person>>() {});

    assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertFalse(response.getBody().isEmpty());

    List<Person> personResponse = response.getBody().stream()
        .filter(it -> it.getId().equals(id))
        .collect(Collectors.toList());

    assertFalse(personResponse.isEmpty());
    assertEquals(1, personResponse.size());
    assertPerson(person, personResponse.get(0));
  }

  @Test
  void getPersonByIdAfterPost() throws MalformedURLException {
    Person person = new Person("Kevin", 23);
    ResponseEntity<Person> postResponse = template.postForEntity(
        new URL("http://localhost:" + port + "/persons").toString(),
        person,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, postResponse.getHeaders().getContentType());
    assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
    assertNotNull(postResponse.getBody());
    assertPerson(person, postResponse.getBody());

    final Long id = postResponse.getBody().getId();

    ResponseEntity<Optional<Person>> getResponse = template.exchange(
        new URL("http://localhost:" + port + "/persons/" + id).toString(),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Optional<Person>>() {});

    assertEquals(MediaType.APPLICATION_JSON_UTF8, getResponse.getHeaders().getContentType());
    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    assertNotNull(getResponse.getBody());
    assertTrue(getResponse.getBody().isPresent());
    assertPerson(person, getResponse.getBody().get());
  }

  @Test
  void getPersonByInvalidId() throws MalformedURLException {
    ResponseEntity<Optional<Person>> getResponse = template.exchange(
        new URL("http://localhost:" + port + "/persons/invalidId").toString(),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Optional<Person>>() {});

    assertEquals(MediaType.APPLICATION_JSON_UTF8, getResponse.getHeaders().getContentType());
    assertEquals(HttpStatus.BAD_REQUEST, getResponse.getStatusCode());
  }

  @Test
  void getFromRoot() throws MalformedURLException {
    ResponseEntity<Optional<Person>> getResponse = template.exchange(
        new URL("http://localhost:" + port + "/").toString(),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Optional<Person>>() {});

    assertEquals(MediaType.APPLICATION_JSON_UTF8, getResponse.getHeaders().getContentType());
    assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
  }

}
