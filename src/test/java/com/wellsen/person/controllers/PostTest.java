package com.wellsen.person.controllers;

import com.wellsen.person.models.Person;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostTest extends ControllerTest {

  @Test
  void postPerson() throws Exception {
    Person person = new Person("Wellsen", 39);
    when(service.create(any(Person.class))).thenReturn(person);

    mvc.perform(MockMvcRequestBuilders.post("/persons")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(asJsonString(person)))
        .andExpect(jsonPath("$.name").value(person.getName()))
        .andExpect(jsonPath("$.age").value(person.getAge()))
        .andDo(print());
  }

  @Test
  void postWithInvalidName() throws Exception {
    Person person = new Person("W", 21);

    mvc.perform(MockMvcRequestBuilders.post("/persons")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void postWithInvalidAge() throws Exception {
    Person person = new Person("Wellsen", -99);

    mvc.perform(MockMvcRequestBuilders.post("/persons")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void postWithInvalidNameAndAge() throws Exception {
    Person person = new Person("W", -99);

    mvc.perform(MockMvcRequestBuilders.post("/persons")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void postWithNoName() throws Exception {
    Person person = new Person(19);

    mvc.perform(MockMvcRequestBuilders.post("/persons")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void postPersonToInvalidPath() throws Exception {
    Person person = new Person("Wellsen", 39);

    mvc.perform(MockMvcRequestBuilders.post("/persons/12345")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isMethodNotAllowed())
        .andDo(print());
  }

  @Test
  void postToRoot() throws Exception {
    Person person = new Person("Wellsen", 39);

    mvc.perform(MockMvcRequestBuilders.post("/")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

}
