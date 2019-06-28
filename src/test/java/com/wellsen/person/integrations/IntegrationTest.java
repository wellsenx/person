package com.wellsen.person.integrations;

import com.wellsen.person.PersonTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest extends PersonTest {

  @LocalServerPort
  int port;

  @Autowired
  TestRestTemplate template;

  @Before
  public void setup() {
    template.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
  }

}
