package com.wellsen.person.services;

import com.wellsen.person.exceptions.BadRequestException;
import com.wellsen.person.exceptions.ResourceNotFoundException;
import com.wellsen.person.models.Person;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PatchTest extends ServiceTest {

  @Test
  public void patchPerson() {
    Optional<Person> person = Optional.of(new Person("Wellsen", 39));
    when(repository.findById(person.get().getId())).thenReturn(person);

    Person personToPatch = new Person("Kevin", 21);
    when(repository.save(any(Person.class))).thenReturn(personToPatch);

    Person response = service.patch(person.get().getId(), personToPatch);

    verify(repository, times(1)).findById(person.get().getId());
    verify(repository, times(1)).save(person.get());
    verifyNoMoreInteractions(repository);

    assertNotNull(response);
    assertEquals(personToPatch, response);
    assertPerson(personToPatch, response);
  }

  @Test
  public void patchName() {
    Optional<Person> person = Optional.of(new Person("Wellsen", 39));
    when(repository.findById(person.get().getId())).thenReturn(person);

    Person personToPatch = new Person("Kevin");
    Person expectedPerson = new Person(personToPatch.getName(), person.get().getAge());
    when(repository.save(any(Person.class))).thenReturn(expectedPerson);

    Person response = service.patch(person.get().getId(), personToPatch);

    verify(repository, times(1)).findById(person.get().getId());
    verify(repository, times(1)).save(person.get());
    verifyNoMoreInteractions(repository);

    assertNotNull(response);
    assertEquals(expectedPerson, response);
    assertPerson(expectedPerson, response);
  }

  @Test
  public void patchAge() {
    Optional<Person> person = Optional.of(new Person("Wellsen", 39));
    when(repository.findById(person.get().getId())).thenReturn(person);

    Person personToPatch = new Person(21);
    Person expectedPerson = new Person(personToPatch.getName(), person.get().getAge());
    when(repository.save(any(Person.class))).thenReturn(expectedPerson);

    Person response = service.patch(person.get().getId(), personToPatch);

    verify(repository, times(1)).findById(person.get().getId());
    verify(repository, times(1)).save(person.get());
    verifyNoMoreInteractions(repository);

    assertNotNull(response);
    assertEquals(response, expectedPerson);
    assertPerson(response, expectedPerson);
  }

  @Test(expected = BadRequestException.class)
  public void patchPersonWithNameLessThan3Chars() throws BadRequestException {
    Optional<Person> person = Optional.of(new Person("Wellsen", 39));
    when(repository.findById(person.get().getId())).thenReturn(person);

    Person personToPatch = new Person("KV");

    service.patch(person.get().getId(), personToPatch);

    verify(repository, times(1)).findById(person.get().getId());
    verify(repository, times(1)).save(personToPatch);
    verifyNoMoreInteractions(repository);
  }

  @Test(expected = BadRequestException.class)
  public void patchPersonWithNameMoreThan40Chars() throws BadRequestException {
    Optional<Person> person = Optional.of(new Person("Wellsen", 39));
    when(repository.findById(person.get().getId())).thenReturn(person);

    Person personToPatch = new Person("12345678901234567890123456789012345678901");

    service.patch(person.get().getId(), personToPatch);

    verify(repository, times(1)).findById(person.get().getId());
    verify(repository, times(1)).save(personToPatch);
    verifyNoMoreInteractions(repository);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void patchPersonWithInvalidId() throws ResourceNotFoundException {
    Person person = new Person();

    service.patch(person.getId(), new Person());

    verify(repository, times(1)).findById(person.getId());
    verifyNoMoreInteractions(repository);
  }

}
