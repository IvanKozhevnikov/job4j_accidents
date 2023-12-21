package ru.job4j.accidents.service.data;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.data.AccidentRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentRepository accidentsRepository;

    public void create(Accident accident) {
        accidentsRepository.save(accident);
    }

    public List<Accident> findAll() {
        return accidentsRepository.findAll();
    }

    public Optional<Accident> findById(int id) {
        return accidentsRepository.findById(id);
    }

    public boolean update(Accident accident) {
        accidentsRepository.save(accident);
        return true;
    }
}
