package com.wellsen.person.repositories;

import com.wellsen.person.models.Person;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

  @SuppressWarnings("unused")
  @Query(value = "select * from persons where name = ?1", nativeQuery = true)
  List<Person> findByName(String name);

}
