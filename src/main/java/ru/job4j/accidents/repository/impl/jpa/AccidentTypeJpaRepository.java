package ru.job4j.accidents.repository.impl.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;

public interface AccidentTypeJpaRepository extends CrudRepository<AccidentType, Integer> {

    @Override
    List<AccidentType> findAll();
}
