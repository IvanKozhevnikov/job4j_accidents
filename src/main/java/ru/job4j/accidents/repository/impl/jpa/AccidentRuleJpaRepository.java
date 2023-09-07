package ru.job4j.accidents.repository.impl.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

import java.util.HashSet;
import java.util.Set;

public interface AccidentRuleJpaRepository extends CrudRepository<Rule, Integer> {

    @Override
    HashSet<Rule> findAll();

    @Override
    Set<Rule> findAllById(Iterable<Integer> ids);
}
