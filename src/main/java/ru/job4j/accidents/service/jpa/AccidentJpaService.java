package ru.job4j.accidents.service.jpa;

import lombok.AllArgsConstructor;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.impl.jpa.AccidentJpaRepository;
import ru.job4j.accidents.service.impl.AccidentService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AccidentJpaService implements AccidentService {

    private final AccidentJpaRepository accidentJpaRepository;

    @Override
    public Accident create(Accident accident) {
        return accidentJpaRepository.save(accident);
    }

    @Override
    public boolean update(Accident accident) {
        if (!accidentJpaRepository.existsById(accident.getId())) {
            return false;
        }
        accidentJpaRepository.save(accident);
        return true;
    }

    @Override
    public boolean deleteById(int id) {
        if (!accidentJpaRepository.existsById(id)) {
            throw new IllegalArgumentException("Accident not found");
        }
        accidentJpaRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentJpaRepository.findById(id);
    }

    @Override
    public List<Accident> findAll() {
        return accidentJpaRepository.findAll();
    }
}
