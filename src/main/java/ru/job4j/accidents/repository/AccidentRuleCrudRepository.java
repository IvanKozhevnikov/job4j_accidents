package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

import java.util.Set;

public interface AccidentRuleCrudRepository extends CrudRepository<Rule, Integer> {

    @Override
    Iterable<Rule> findAll();

    @Override
    Set<Rule> findAllById(Iterable<Integer> ids);
}
