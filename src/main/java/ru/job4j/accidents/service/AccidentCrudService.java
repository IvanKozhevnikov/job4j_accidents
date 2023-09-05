package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentCrudRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentCrudService implements AccidentService {

    private final AccidentCrudRepository accidentCrudRepository;

    @Override
    public Accident create(Accident accident) {
        return accidentCrudRepository.save(accident);
    }

    @Override
    public boolean update(Accident accident) {
        if (!accidentCrudRepository.existsById(accident.getId())) {
            throw new IllegalArgumentException("Accident not found");
        }
        accidentCrudRepository.save(accident);
        return true;
    }

    @Override
    public boolean deleteById(int id) {
        if (!accidentCrudRepository.existsById(id)) {
            throw new IllegalArgumentException("Accident not found");
        }
        accidentCrudRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentCrudRepository.findById(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return (Collection<Accident>) accidentCrudRepository.findAll();
    }
}
