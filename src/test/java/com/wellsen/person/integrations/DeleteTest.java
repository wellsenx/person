package com.wellsen.person.integrations;

import com.wellsen.person.models.Person;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class DeleteTest extends IntegrationTest {

  @Test
  void deletePersonAfterPost() throws MalformedURLException {
    Person wellsen = new Person("Wellsen", 39);
    ResponseEntity<Person> postResponse = template.postForEntity(
        new URL("http://localhost:" + port + "/persons").toString(),
        wellsen,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, postResponse.getHeaders().getContentType());
    assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
    assertNotNull(postResponse.getBody());
    assertPerson(wellsen, postResponse.getBody());

    final Long id = postResponse.getBody().getId();

    ResponseEntity<Object> response = template.exchange(
        new URL("http://localhost:" + port + "/persons/" + id).toString(),
        HttpMethod.DELETE,
        null,
        Object.class);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertNull(response.getBody());

    ResponseEntity<Optional<Person>> getResponse = template.exchange(
        new URL("http://localhost:" + port + "/persons/" + id).toString(),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Optional<Person>>() {});

    assertEquals(MediaType.APPLICATION_JSON_UTF8, getResponse.getHeaders().getContentType());
    assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
  }

  @Test
  void deletePersonOnInvalidId() throws MalformedURLException {
    ResponseEntity<Object> response = template.exchange(
        new URL("http://localhost:" + port + "/persons/invalidId").toString(),
        HttpMethod.DELETE,
        null,
        Object.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void deletePersonThatNotExist() throws MalformedURLException {
    ResponseEntity<Object> response = template.exchange(
        new URL("http://localhost:" + port + "/persons/2").toString(),
        HttpMethod.DELETE,
        null,
        Object.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void deletePersonOnInvalidPath() throws MalformedURLException {
    ResponseEntity<Object> response = template.exchange(
        new URL("http://localhost:" + port + "/persons").toString(),
        HttpMethod.DELETE,
        null,
        Object.class);

    assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
  }

  @Test
  void deleteOnRoot() throws MalformedURLException {
    ResponseEntity<Object> response = template.exchange(
        new URL("http://localhost:" + port + "/persons/2").toString(),
        HttpMethod.DELETE,
        null,
        Object.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

}
