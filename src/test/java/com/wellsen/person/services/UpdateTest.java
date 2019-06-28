package com.wellsen.person.services;

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
public class UpdateTest extends ServiceTest {

  @Test
  public void updatePerson() {
    Optional<Person> person = Optional.of(new Person());
    when(repository.findById(person.get().getId())).thenReturn(person);

    Person personToUpdate = new Person("Kevin", 21);
    when(repository.save(any(Person.class))).thenReturn(personToUpdate);

    Person response = service.update(person.get().getId(), personToUpdate);

    verify(repository, times(1)).findById(person.get().getId());
    verify(repository, times(1)).save(person.get());
    verifyNoMoreInteractions(repository);

    assertNotNull(response);
    assertEquals(personToUpdate, response);
    assertPerson(personToUpdate, response);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void updatePersonWithInvalidId() throws ResourceNotFoundException {
    Person person = new Person();

    service.update(person.getId(), new Person());

    verify(repository, times(1)).findById(person.getId());
    verifyNoMoreInteractions(repository);
  }

}
