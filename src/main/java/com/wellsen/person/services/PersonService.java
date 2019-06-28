package com.wellsen.person.services;

import com.wellsen.person.models.Person;
import java.util.List;

public interface PersonService {

  List<Person> read();

  Person create(Person person);

  Person update(Long id, Person person);

  Person patch(Long id, Person person);

  void delete(Long id);

}
