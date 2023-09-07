package ru.job4j.accidents.repository.impl.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.List;

public interface AccidentJpaRepository extends CrudRepository<Accident, Integer> {

    @Override
    List<Accident> findAll();
}
