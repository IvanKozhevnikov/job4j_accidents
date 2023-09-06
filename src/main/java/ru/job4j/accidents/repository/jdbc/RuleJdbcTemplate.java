package ru.job4j.accidents.repository.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@Repository
@AllArgsConstructor
public class RuleJdbcTemplate {

    private static final String SELECT_ALL_RULE_QUERY = "select id, name from rules";

    private static final String SELECT_BY_ID_RULE_QUERY = "select id, name from rules where id = ?";

    private final JdbcTemplate jdbc;


    public HashSet<Rule> findAll() {
        HashSet<Rule> rules = new HashSet<>();
        jdbc.query(SELECT_ALL_RULE_QUERY, resultSet -> {
            do {
                Rule rule = new Rule();
                rule.setId(resultSet.getInt("id"));
                rule.setName(resultSet.getString("name"));
                rules.add(rule);
            } while (resultSet.next());
        });
        return rules;
    }

    public Optional<Rule> findById(int id) {
        Rule rule = jdbc.queryForObject(SELECT_BY_ID_RULE_QUERY, new Object[]{id}, (rs, rowNum) -> {
            int ruleId = rs.getInt("id");
            String ruleName = rs.getString("name");
            return new Rule(ruleId, ruleName);
        });
        return Optional.ofNullable(rule);
    }
}