package com.wellsen.person.services;

import com.wellsen.person.PersonTest;
import com.wellsen.person.repositories.PersonRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class ServiceTest extends PersonTest {

  @Mock
  PersonRepository repository;

  @InjectMocks
  PersonServiceImpl service;

}
