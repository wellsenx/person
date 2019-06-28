package com.wellsen.person.controllers;

import com.wellsen.person.exceptions.BadRequestException;
import com.wellsen.person.exceptions.ResourceNotFoundException;
import com.wellsen.person.models.Person;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatchTest extends ControllerTest {

  @Test
  void patchPerson() throws Exception {
    final Long id = 12345L;
    Person person = new Person("Wellsen", 39);
    when(service.patch(eq(id), any(Person.class))).thenReturn(person);

    mvc.perform(MockMvcRequestBuilders.patch("/persons/" + id)
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
  void patchName() throws Exception {
    final Long id = 12345L;
    Person person = new Person("Wellsen", 39);
    Person personToPatch = new Person("Kevin");
    Person expected = new Person(personToPatch.getName(), person.getAge());
    when(service.patch(eq(id), any(Person.class))).thenReturn(expected);

    mvc.perform(MockMvcRequestBuilders.patch("/persons/" + id)
        .content(asJsonString(personToPatch))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(asJsonString(expected)))
        .andExpect(jsonPath("$.name").value(expected.getName()))
        .andExpect(jsonPath("$.age").value(expected.getAge()))
        .andDo(print());
  }

  @Test
  void patchAge() throws Exception {
    final Long id = 12345L;
    Person person = new Person("Wellsen", 39);
    Person personToPatch = new Person(27);
    Person expected = new Person(person.getName(), personToPatch.getAge());
    when(service.patch(eq(id), any(Person.class))).thenReturn(expected);

    mvc.perform(MockMvcRequestBuilders.patch("/persons/" + id)
        .content(asJsonString(personToPatch))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(asJsonString(expected)))
        .andExpect(jsonPath("$.name").value(expected.getName()))
        .andExpect(jsonPath("$.age").value(expected.getAge()))
        .andDo(print());
  }

  @Test
  void patchWithInvalidName() throws Exception {
    Person person = new Person("W");
    when(service.patch(anyLong(), any(Person.class))).thenThrow(new BadRequestException("Name should be 3 to 40 chars"));

    mvc.perform(MockMvcRequestBuilders.patch("/persons/12345")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void patchWithInvalidAge() throws Exception {
    Person person = new Person(-99);
    when(service.patch(anyLong(), any(Person.class))).thenThrow(BadRequestException.class);

    mvc.perform(MockMvcRequestBuilders.patch("/persons/12345")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void patchWithInvalidNameAndAge() throws Exception {
    Person person = new Person("W", -99);
    when(service.patch(anyLong(), any(Person.class))).thenThrow(BadRequestException.class);

    mvc.perform(MockMvcRequestBuilders.patch("/persons/12345")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void patchPersonOnEmptyData() throws Exception {
    final Long id = 12345L;
    Person person = new Person("Wellsen", 39);
    when(service.patch(eq(id), any(Person.class)))
        .thenThrow(new ResourceNotFoundException("Person not found with id " + id));

    mvc.perform(MockMvcRequestBuilders.patch("/persons/" + id)
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  void patchPersonByInvalidId() throws Exception {
    Person person = new Person("Wellsen", 39);

    mvc.perform(MockMvcRequestBuilders.patch("/persons/invalidId")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void patchPersonToInvalidPath() throws Exception {
    Person person = new Person("Wellsen", 39);

    mvc.perform(MockMvcRequestBuilders.patch("/persons")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isMethodNotAllowed())
        .andDo(print());
  }

  @Test
  void patchToRoot() throws Exception {
    Person person = new Person("Wellsen", 39);

    mvc.perform(MockMvcRequestBuilders.patch("/")
        .content(asJsonString(person))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

}
