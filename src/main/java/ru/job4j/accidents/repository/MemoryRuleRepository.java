package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

@Repository
@ThreadSafe
public class MemoryRuleRepository implements RuleRepository {

    private final Map<Integer, Rule> rules = new HashMap<>();

    public MemoryRuleRepository() {
        rules.put(1, new Rule(1, "Статья. 1"));
        rules.put(2, new Rule(2, "Статья. 2"));
        rules.put(3, new Rule(3, "Статья. 3"));
    }

    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
    }

    public HashSet<Rule> findAll() {
        return new HashSet<>(rules.values());
    }
}
