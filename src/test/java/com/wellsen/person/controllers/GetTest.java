package com.wellsen.person.controllers;

import com.wellsen.person.exceptions.ResourceNotFoundException;
import com.wellsen.person.models.Person;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetTest extends ControllerTest {

  @Test
  void getPersons() throws Exception {
    ArrayList<Person> persons = new ArrayList<Person>() {
      {
        add(new Person("Wellsen", 39));
        add(new Person("Kevin", 21));
      }
    };
    when(service.read()).thenReturn(persons);

    mvc.perform(MockMvcRequestBuilders.get("/persons")
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(asJsonString(persons)))
        .andExpect(jsonPath("$", hasSize(2)))
        .andDo(print());
  }

  @Test
  void getPersonsOnEmptyData() throws Exception {
    when(service.read()).thenReturn(new ArrayList<>());

    mvc.perform(MockMvcRequestBuilders.get("/persons")
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(0)))
        .andDo(print());
  }

  @Test
  void getPersonById() throws Exception {
    final Long id = 12345L;
    Optional<Person> person = Optional.of(new Person("Wellsen", 39));
    person.get().setId(id);
    when(service.read(id)).thenReturn(person);

    mvc.perform(MockMvcRequestBuilders.get("/persons/" + id)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(asJsonString(person.get())))
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.name").value(person.get().getName()))
        .andExpect(jsonPath("$.age").value(person.get().getAge()))
        .andDo(print());
  }

  @Test
  void getPersonByIdOnEmptyData() throws Exception {
    final Long id = 12345L;
    when(service.read(id)).thenThrow(new ResourceNotFoundException("Person not found with id " + id));

    mvc.perform(MockMvcRequestBuilders.get("/persons/" + id)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  void getPersonByInvalidId() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/persons/invalidId")
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void getFromRoot() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/")
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

}
