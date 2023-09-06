package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRuleCrudRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccidentRuleCurdService implements RuleService {

    private final AccidentRuleCrudRepository accidentRuleCrudRepository;

    @Override
    public Optional<Rule> findById(int id) {
        return accidentRuleCrudRepository.findById(id);
    }

    @Override
    public Iterable<Rule> findAll() {
        return  accidentRuleCrudRepository.findAll();
    }

    @Override
    public Set<Rule> findByIds(String[] ids) {
        return new HashSet<>(
                accidentRuleCrudRepository.findAllById(
                        Arrays.stream(ids)
                                .map(Integer::parseInt)
                                .collect(Collectors.toSet())));
    }
}
