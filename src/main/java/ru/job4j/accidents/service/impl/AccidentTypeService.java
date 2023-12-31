package ru.job4j.accidents.service.impl;

import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

public interface AccidentTypeService {

    Optional<AccidentType> findById(int id);

    List<AccidentType> findAll();
}