package ru.job4j.accidents.service.impl;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface AccidentService {

    Accident create(Accident accident);

    boolean update(Accident accident);

    boolean deleteById(int id);

    Optional<Accident> findById(int id);

    Collection<Accident> findAll();
}
