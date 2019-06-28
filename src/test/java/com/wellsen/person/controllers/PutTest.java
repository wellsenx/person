package com.wellsen.person.controllers;

import com.wellsen.person.exceptions.ResourceNotFoundException;
import com.wellsen.person.models.Person;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PutTest extends ControllerTest {

  @Test
  void putPerson() throws Exception {
    final Long id = 12345L;
    Person person = new Person("Wellsen", 39);
    when(service.update(eq(id), any(Person.class))).thenReturn(person);

    mvc.perform(MockMvcRequestBuilders.put("/persons/" + id)
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(asJsonString(person)))
        .andExpect(jsonPath("$.name").value(person.getName()))
        .andExpect(jsonPath("$.age").value(person.getAge()))
        .andDo(print());
  }

  @Test
  void putWithInvalidName() throws Exception {
    Person person = new Person("W", 39);

    mvc.perform(MockMvcRequestBuilders.put("/persons/12345")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void putWithInvalidAge() throws Exception {
    Person person = new Person("Wellsen", -99);

    mvc.perform(MockMvcRequestBuilders.put("/persons/12345")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void putWithInvalidNameAndAge() throws Exception {
    Person person = new Person("W", -99);

    mvc.perform(MockMvcRequestBuilders.put("/persons/12345")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void putWithNoName() throws Exception {
    Person person = new Person(19);

    mvc.perform(MockMvcRequestBuilders.put("/persons/12345")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void putPersonOnEmptyData() throws Exception {
    final Long id = 12345L;
    Person person = new Person("Wellsen", 39);
    when(service.update(eq(id), any(Person.class)))
        .thenThrow(new ResourceNotFoundException("Person not found with id " + id));

    mvc.perform(MockMvcRequestBuilders.put("/persons/" + id)
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  void putPersonByInvalidId() throws Exception {
    Person person = new Person("Wellsen", 39);

    mvc.perform(MockMvcRequestBuilders.put("/persons/invalidId")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void putPersonToInvalidPath() throws Exception {
    Person person = new Person("Wellsen", 39);

    mvc.perform(MockMvcRequestBuilders.put("/persons")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isMethodNotAllowed())
        .andDo(print());
  }

  @Test
  void postToRoot() throws Exception {
    Person person = new Person("Wellsen", 39);

    mvc.perform(MockMvcRequestBuilders.put("/")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

}
