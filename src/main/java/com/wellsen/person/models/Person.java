package com.wellsen.person.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "persons")
public class Person extends BaseModel {

  @NotBlank
  @Size(min = 3, max = 40, message = "Name should be 3 to 40 chars")
  @Column(name = "name", length = 40)
  private String name;

  @Min(value = 0, message = "Age should be positive")
  @Max(value = 120, message = "Age should not exceed 120")
  @Column(name = "age")
  private Integer age;

  public Person(
      @NotBlank @Size(min = 3, max = 40, message = "Name should be 3 to 40 chars") String name) {
    this.name = name;
  }

  public Person(@Min(value = 0, message = "Age should be positive") @Max(value = 120,
      message = "Age should not exceed 120") Integer age) {
    this.age = age;
  }

}
