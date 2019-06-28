package com.wellsen.person;

import com.wellsen.person.models.Person;

import static org.junit.Assert.assertEquals;

public class PersonTest {

  protected void assertPerson(Person expected, Person actual) {
    assertEquals(expected.getName(), actual.getName());
    assertEquals(expected.getAge(), actual.getAge());
  }

}
