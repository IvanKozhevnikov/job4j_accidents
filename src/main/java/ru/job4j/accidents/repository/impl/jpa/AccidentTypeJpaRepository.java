package ru.job4j.accidents.repository.impl.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.AccidentType;

public interface AccidentTypeJpaRepository extends CrudRepository<AccidentType, Integer> {

    @Override
    Iterable<AccidentType> findAll();
}
