package com.wellsen.person.controllers;

import com.wellsen.person.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteTest extends ControllerTest {

  @Test
  void deletePerson() throws Exception {
    mvc.perform(MockMvcRequestBuilders.delete("/persons/12345")
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNoContent())
        .andDo(print());
  }

  @Test
  void deletePersonOnEmptyData() throws Exception {
    final Long id = 12345L;
    doThrow(ResourceNotFoundException.class).when(service).delete(id);

    mvc.perform(MockMvcRequestBuilders.delete("/persons/" + id)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  void deletePersonByInvalidId() throws Exception {
    mvc.perform(MockMvcRequestBuilders.delete("/persons/invalidId")
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void deletePersonOnInvalidPath() throws Exception {
    mvc.perform(MockMvcRequestBuilders.delete("/persons")
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isMethodNotAllowed())
        .andDo(print());
  }

  @Test
  void deleteToRoot() throws Exception {
    mvc.perform(MockMvcRequestBuilders.delete("/")
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

}
