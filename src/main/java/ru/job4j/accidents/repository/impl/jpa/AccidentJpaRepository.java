package ru.job4j.accidents.repository.impl.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

public interface AccidentJpaRepository extends CrudRepository<Accident, Integer> {

}
