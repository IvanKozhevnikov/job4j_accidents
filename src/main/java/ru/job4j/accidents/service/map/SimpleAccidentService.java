//package ru.job4j.accidents.service.map;
//
//import lombok.AllArgsConstructor;
//import ru.job4j.accidents.model.Accident;
//import ru.job4j.accidents.repository.hibernate.AccidentHibernate;
//import ru.job4j.accidents.service.impl.AccidentService;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//
//
//@AllArgsConstructor
//public class SimpleAccidentService implements AccidentService {
//
//    private final AccidentHibernate repository;
//
//    @Override
//    public Accident create(Accident accident) {
//        return repository.save(accident);
//    }
//
//    @Override
//    public boolean update(Accident accident) {
//        return repository.update(accident);
//    }
//
//    @Override
//    public boolean deleteById(int id) {
//        return repository.deleteById(id);
//    }
//
//    @Override
//    public Optional<Accident> findById(int id) {
//        return repository.findById(id);
//    }
//
//    @Override
//    public List<Accident> findAll() {
//        return repository.getAll();
//    }
//}
