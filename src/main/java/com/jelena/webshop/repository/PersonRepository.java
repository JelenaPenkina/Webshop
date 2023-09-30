package com.jelena.webshop.repository;

import com.jelena.webshop.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
