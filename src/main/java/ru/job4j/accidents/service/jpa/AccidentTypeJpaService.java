package ru.job4j.accidents.service.jpa;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.impl.jpa.AccidentTypeJpaRepository;
import ru.job4j.accidents.service.impl.AccidentTypeService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeJpaService implements AccidentTypeService {

    private final AccidentTypeJpaRepository accidentTypeCrudRepository;

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeCrudRepository.findById(id);
    }

    @Override
    public List<AccidentType> findAll() {
        return  accidentTypeCrudRepository.findAll();
    }
}
