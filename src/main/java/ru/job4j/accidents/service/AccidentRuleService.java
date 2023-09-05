package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentCrudRepository;
import ru.job4j.accidents.repository.AccidentRuleCrudRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccidentRuleService implements RuleService {

    private final AccidentRuleCrudRepository accidentRuleCrudRepository;

    @Override
    public Optional<Rule> findById(int id) {
        return accidentRuleCrudRepository.findById(id);
    }

    @Override
    public Collection<Rule> findAll() {
        return (Collection<Rule>) accidentRuleCrudRepository.findAll();
    }

    @Override
    public Set<Rule> findByIds(String[] ids) {
        Set<Rule> rules = new HashSet<>();
        for (String i : ids) {
            findById(Integer.parseInt(i)).ifPresent(rules::add);
        }
        return rules;
    }
}
