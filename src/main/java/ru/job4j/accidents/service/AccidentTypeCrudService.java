package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeCrudRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeCrudService implements AccidentTypeService {

    private final AccidentTypeCrudRepository accidentTypeCrudRepository;

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeCrudRepository.findById(id);
    }

    @Override
    public Iterable<AccidentType> findAll() {
        return  accidentTypeCrudRepository.findAll();
    }
}
