package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Integer> {

    Author findByAuthority(String authority);
}
