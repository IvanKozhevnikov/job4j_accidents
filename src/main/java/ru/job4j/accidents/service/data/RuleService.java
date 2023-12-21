package ru.job4j.accidents.service.data;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.data.RuleRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RuleService {

    private final RuleRepository ruleRepository;

    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }

    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

    public Set<Rule> findByIds(String[] ids) {
        return (Set<Rule>) ruleRepository.findAllById(
                Arrays.stream(ids)
                        .map(Integer::parseInt)
                        .collect(Collectors.toSet()));
    }
}
