package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.HashSet;
import java.util.Optional;

public interface RuleRepository {

    Optional<Rule> findById(int id);

    HashSet<Rule> findAll();
}
