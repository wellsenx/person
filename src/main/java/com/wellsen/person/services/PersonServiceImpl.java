package com.wellsen.person.services;

import com.wellsen.person.exceptions.BadRequestException;
import com.wellsen.person.exceptions.ResourceNotFoundException;
import com.wellsen.person.models.Person;
import com.wellsen.person.repositories.PersonRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

  private final PersonRepository repository;

  public PersonServiceImpl(PersonRepository repository) {
    this.repository = repository;
  }

  public List<Person> read() {
    return repository.findAll();
  }

  public Optional<Person> read(Long id) {
    return repository.findById(id)
        .map(Optional::of)
        .orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));
  }

  public Person create(Person person) {
    return repository.save(person);
  }

  public Person update(Long id, Person person) {
    return repository.findById(id)
        .map(it -> {
          it.setName(person.getName());
          it.setAge(person.getAge());
          return repository.save(it);
        }).orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));
  }

  public Person patch(Long id, Person person) {
    return repository.findById(id)
        .map(it -> {
          if (person.getName() != null) {
            if (person.getName().length() < 3 || person.getName().length() > 40) {
              throw new BadRequestException("Name should be 3 to 40 chars");
            }
            it.setName(person.getName());
          }
          if (person.getAge() != null) {
            if (person.getAge() < 0) {
              throw new BadRequestException("Age should be positive");
            }
            if (person.getAge() > 120) {
              throw new BadRequestException("Age should not exceed 120");
            }
            it.setAge(person.getAge());
          }
          return repository.save(it);
        }).orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));
  }

  public void delete(Long id) {
    repository.findById(id).map(it -> {
      repository.delete(it);
      return it;
    }).orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));
  }

}
