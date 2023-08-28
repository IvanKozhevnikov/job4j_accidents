package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;

import java.util.HashSet;
import java.util.Optional;


public interface RuleService {

    Optional<Rule> findById(int id);

    HashSet<Rule> findAll();
}
