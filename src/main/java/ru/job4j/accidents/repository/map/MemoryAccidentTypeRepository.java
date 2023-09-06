package ru.job4j.accidents.repository.map;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.impl.AccidentTypeRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@ThreadSafe
public class MemoryAccidentTypeRepository implements AccidentTypeRepository {

    private final Map<Integer, AccidentType> accidentType = new ConcurrentHashMap<>() {
    };

    public MemoryAccidentTypeRepository() {
        accidentType.put(1, new AccidentType(1, "Две машины"));
        accidentType.put(2, new AccidentType(2, "Машина и человек"));
        accidentType.put(3, new AccidentType(3, "Машина и велосипед"));
    }

    @Override
    public Collection<AccidentType> findAll() {
        return accidentType.values();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(accidentType.get(id));
    }
}