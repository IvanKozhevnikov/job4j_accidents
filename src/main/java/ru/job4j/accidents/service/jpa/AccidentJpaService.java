package ru.job4j.accidents.service.jpa;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.impl.jpa.AccidentJpaRepository;
import ru.job4j.accidents.service.impl.AccidentService;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentJpaService implements AccidentService {

    private final AccidentJpaRepository accidentCrudRepository;

    @Override
    public Accident create(Accident accident) {
        return accidentCrudRepository.save(accident);
    }

    @Override
    public boolean update(Accident accident) {
        if (!accidentCrudRepository.existsById(accident.getId())) {
            return false;
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
