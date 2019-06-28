package com.wellsen.person.integrations;

import com.wellsen.person.models.Person;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PostTest extends IntegrationTest {

  @Test
  void postPerson() throws MalformedURLException {
    Person person = new Person("Wellsen", 39);
    ResponseEntity<Person> response = template.postForEntity(
        new URL("http://localhost:" + port + "/persons").toString(),
        person,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertPerson(person, response.getBody());
  }

  @Test
  void postWithInvalidName() throws MalformedURLException {
    Person person = new Person("W", 21);
    ResponseEntity<Person> response = template.postForEntity(
        new URL("http://localhost:" + port + "/persons").toString(),
        person,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void postWithInvalidAge() throws MalformedURLException {
    Person person = new Person("Wellsen", -99);
    ResponseEntity<Person> response = template.postForEntity(
        new URL("http://localhost:" + port + "/persons").toString(),
        person,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void postWithInvalidNameAndAge() throws MalformedURLException {
    Person person = new Person("W", -99);
    ResponseEntity<Person> response = template.postForEntity(
        new URL("http://localhost:" + port + "/persons").toString(),
        person,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void postWithNoName() throws MalformedURLException {
    Person person = new Person(19);
    ResponseEntity<Person> response = template.postForEntity(
        new URL("http://localhost:" + port + "/persons").toString(),
        person,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void postPersonToInvalidPath() throws MalformedURLException {
    Person person = new Person("Wellsen", 39);
    ResponseEntity<Person> response = template.postForEntity(
        new URL("http://localhost:" + port + "/persons/12345").toString(),
        person,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
  }

  @Test
  void postToRoot() throws MalformedURLException {
    Person person = new Person("Wellsen", 39);
    ResponseEntity<Person> response = template.postForEntity(
        new URL("http://localhost:" + port + "/").toString(),
        person,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

}
