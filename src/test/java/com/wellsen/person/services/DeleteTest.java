package com.wellsen.person.services;

import com.wellsen.person.exceptions.ResourceNotFoundException;
import com.wellsen.person.models.Person;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class DeleteTest extends ServiceTest {

  @Test
  public void deletePerson() {
    Optional<Person> person = Optional.of(new Person("Wellsen", 39));
    when(repository.findById(person.get().getId())).thenReturn(person);

    service.delete(person.get().getId());

    verify(repository, times(1)).findById(person.get().getId());
    verify(repository, times(1)).delete(person.get());
    verifyNoMoreInteractions(repository);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void deletePersonWithInvalidId() throws ResourceNotFoundException {
    Person person = new Person();

    service.delete(person.getId());

    verify(repository, times(1)).findById(person.getId());
    verifyNoMoreInteractions(repository);
  }

}
