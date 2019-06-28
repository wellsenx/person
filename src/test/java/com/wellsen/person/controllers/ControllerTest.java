package com.wellsen.person.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wellsen.person.PersonTest;
import com.wellsen.person.services.PersonServiceImpl;
import java.io.IOException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
class ControllerTest extends PersonTest {

  @Autowired
  MockMvc mvc;

  @MockBean
  PersonServiceImpl service;

  @Autowired
  ObjectMapper mapper;

  static String asJsonString(final Object obj) throws RuntimeException {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
