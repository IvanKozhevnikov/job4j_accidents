package ru.job4j.accidents.service.map;

import lombok.AllArgsConstructor;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.hibernate.AccidentTypeHibernate;
import ru.job4j.accidents.service.impl.AccidentTypeService;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
public class SimpleAccidentTypeService implements AccidentTypeService {

    private final AccidentTypeHibernate accidentTypeRepository;

    @Override
    public List<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }
}