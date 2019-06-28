package com.wellsen.person.integrations;

import com.wellsen.person.models.Person;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PutTest extends IntegrationTest {

  @Test
  void putPersonAfterPost() throws MalformedURLException {
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
    Person kevin = new Person("Kevin", 23);
    HttpEntity<Person> entity = new HttpEntity<>(kevin);

    ResponseEntity<Person> response = template.exchange(
        new URL("http://localhost:" + port + "/persons/" + id).toString(),
        HttpMethod.PUT,
        entity,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertPerson(kevin, response.getBody());
  }

  @Test
  void putWithInvalidName() throws MalformedURLException {
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
    Person kevin = new Person("K", 23);
    HttpEntity<Person> entity = new HttpEntity<>(kevin);

    ResponseEntity<Person> response = template.exchange(
        new URL("http://localhost:" + port + "/persons/" + id).toString(),
        HttpMethod.PUT,
        entity,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void putWithInvalidAge() throws MalformedURLException {
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
    Person kevin = new Person("Kevin", -99);
    HttpEntity<Person> entity = new HttpEntity<>(kevin);

    ResponseEntity<Person> response = template.exchange(
        new URL("http://localhost:" + port + "/persons/" + id).toString(),
        HttpMethod.PUT,
        entity,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void putWithInvalidNameAndAge() throws MalformedURLException {
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
    Person kevin = new Person("K", -99);
    HttpEntity<Person> entity = new HttpEntity<>(kevin);

    ResponseEntity<Person> response = template.exchange(
        new URL("http://localhost:" + port + "/persons/" + id).toString(),
        HttpMethod.PUT,
        entity,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void putWithNoName() throws MalformedURLException {
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
    Person kevin = new Person(23);
    HttpEntity<Person> entity = new HttpEntity<>(kevin);

    ResponseEntity<Person> response = template.exchange(
        new URL("http://localhost:" + port + "/persons/" + id).toString(),
        HttpMethod.PUT,
        entity,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void putPersonToInvalidPath() throws MalformedURLException {
    Person person = new Person("Wellsen", 39);
    HttpEntity<Person> entity = new HttpEntity<>(person);

    ResponseEntity<Person> response = template.exchange(
        new URL("http://localhost:" + port + "/persons/2").toString(),
        HttpMethod.PUT,
        entity,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void putToRoot() throws MalformedURLException {
    Person person = new Person("Wellsen", 39);
    HttpEntity<Person> entity = new HttpEntity<>(person);

    ResponseEntity<Person> response = template.exchange(
        new URL("http://localhost:" + port + "/").toString(),
        HttpMethod.PUT,
        entity,
        Person.class);

    assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

}
