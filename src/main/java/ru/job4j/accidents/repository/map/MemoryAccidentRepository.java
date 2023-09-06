package ru.job4j.accidents.repository.map;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.impl.AccidentRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class MemoryAccidentRepository implements AccidentRepository {

    private final AtomicInteger nextId = new AtomicInteger(0);
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final MemoryAccidentTypeRepository accidentType = new MemoryAccidentTypeRepository();
    private final MemoryRuleRepository memoryRuleRepository = new MemoryRuleRepository();

    public MemoryAccidentRepository() {
        create(new Accident(1, "Accident 1", "Парковка в неположенном месте",
                "ул. Гагарина д.9", new AccidentType(1, "Две машины"),
                Set.of(new Rule(1, "Статья. 1"))));
        create(new Accident(2, "Accident 2", "Отключены габаритные огни",
                "ул. Советская д.11", new AccidentType(2, "Машина и человек"),
                Set.of(new Rule(2, "Статья. 2"))));
        create(new Accident(3, "Accident 3", "Не уступил дорогу при помехе с права",
                "ул. Винокурова д.15", new AccidentType(3, "Машина и велосипед"),
                Set.of(new Rule(3, "Статья. 3"))));
    }

    @Override
    public Accident create(Accident accident) {
        int id = nextId.incrementAndGet();
        accident.setId(id);
        return accidents.put(id, accident);
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (key, value) -> {
            return new Accident(
                    value.getId(), accident.getName(), accident.getText(),
                    accident.getAddress(), new AccidentType(accident.getType().getId(),
                    accidentType.findById(accident.getType().getId()).get().getName()),
                    (accident.getRules()));
        }) != null;
    }

    @Override
    public boolean deleteById(int id) {
        return accidents.remove(id) != null;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.of(accidents.get(id));
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }
}