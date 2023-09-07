package ru.job4j.accidents.service.jpa;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.impl.jpa.AccidentRuleJpaRepository;
import ru.job4j.accidents.service.impl.RuleService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccidentRuleJpaService implements RuleService {

    private final AccidentRuleJpaRepository accidentRuleCrudRepository;

    @Override
    public Optional<Rule> findById(int id) {
        return accidentRuleCrudRepository.findById(id);
    }

    @Override
    public HashSet<Rule> findAll() {
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
