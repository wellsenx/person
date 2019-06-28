package com.wellsen.person.services;

import com.wellsen.person.models.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class CreateTest extends ServiceTest {

  @Test
  public void createPerson() {
    Person person = new Person("Wellsen", 39);
    when(repository.save(any(Person.class))).thenReturn(person);

    Person response = service.create(person);

    ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
    verify(repository, times(1)).save(argument.capture());
    verifyNoMoreInteractions(repository);

    assertNotNull(response);
    assertEquals(person, response);
    assertPerson(person, argument.getValue());
  }

}
