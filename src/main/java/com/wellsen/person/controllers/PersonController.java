package com.wellsen.person.controllers;

import com.wellsen.person.models.Person;
import com.wellsen.person.services.PersonServiceImpl;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping({"/persons"})
public class PersonController extends BaseController {

  private final PersonServiceImpl service;

  public PersonController(PersonServiceImpl service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<Person>> getPersons() {
    return ResponseEntity.ok(service.read());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<Person>> getPerson(@PathVariable Long id) {
    return ResponseEntity.ok(service.read(id));
  }

  @PostMapping
  public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
    Person savedPerson = service.create(person);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedPerson.getId()).toUri();

    return ResponseEntity.created(location).body(savedPerson);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Person> updatePerson(@PathVariable Long id, @Valid @RequestBody Person person) {
    return ResponseEntity.ok(service.update(id, person));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Person> patchPerson(@PathVariable Long id, @RequestBody Person person) {
    return ResponseEntity.ok(service.patch(id, person));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePerson(@PathVariable Long id) {
    service.delete(id);

    return ResponseEntity.noContent().build();
  }

}
