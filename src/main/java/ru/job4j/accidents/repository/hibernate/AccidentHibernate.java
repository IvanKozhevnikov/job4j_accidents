package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class AccidentHibernate {

    private final CrudRepository crudRepository;

    public Accident save(Accident accident) {
        crudRepository.run(session -> session.save(accident));
        return accident;
    }

    public boolean update(Accident accident) {
        crudRepository.run(session -> session.merge(accident));
        return true;
    }

    public boolean deleteById(int id) {
        Accident accident = new Accident();
        accident.setId(id);
        crudRepository.run(session -> session.delete(accident));
        return true;
    }

    public List<Accident> getAll() {
        return crudRepository.query("from Accident", Accident.class);
    }

    public Optional<Accident> findById(int id) {
        return crudRepository.optional("from Accident as a where a.id=:fId",
                Accident.class, Map.of("id", id));
    }
}