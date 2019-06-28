package com.wellsen.person.services;

import com.wellsen.person.exceptions.ResourceNotFoundException;
import com.wellsen.person.models.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReadTest extends ServiceTest {

  @Test
  public void readPersons() {
    List<Person> persons = new ArrayList<Person>() {
      {
        add(new Person("Wellsen", 39));
        add(new Person("Kevin", 21));
      }
    };
    when(repository.findAll()).thenReturn(persons);

    List<Person> response = service.read();

    verify(repository, times(1)).findAll();
    verifyNoMoreInteractions(repository);

    assertNotNull(response);
    assertEquals(persons, response);
  }

  @Test
  public void readPersonsOnEmptyData() {
    List<Person> response = service.read();

    verify(repository, times(1)).findAll();
    verifyNoMoreInteractions(repository);

    assertNotNull(response);
    assertEquals(0, response.size());
  }

  @Test
  public void readPersonById() {
    Optional<Person> person = Optional.of(new Person("Wellsen", 39));
    when(repository.findById(person.get().getId())).thenReturn(person);

    Optional<Person> response = service.read(person.get().getId());

    verify(repository, times(1)).findById(person.get().getId());
    verifyNoMoreInteractions(repository);

    assertNotNull(response);
    assertEquals(person, response);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void readPersonByIdOnEmptyData() throws ResourceNotFoundException {
    final Long id = 12345L;

    service.read(id);

    verify(repository, times(1)).findById(id);
    verifyNoMoreInteractions(repository);
  }

}
