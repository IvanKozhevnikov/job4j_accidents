package ru.job4j.accidents.repository.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {

    private final JdbcTemplate jdbc;

    private static final String UPDATE_ACCIDENT_QUERY = """
                update accidents
            set name = (?), text = (?), address = (?), accident_types_id = (?)
            where id = (?)
            """;

    private static final String DELETE_ACCIDENT_RULE_WHERE_REQUIRED_ID = """
            delete from accident_rules where accident_id = (?)
            """;

    private static final String SELECT_ACCIDENT_FIND_BY_ID_QUERY = """
            select a.id, a.name as a_name, a.text, a.address, at.id as at_id, at.name as at_name
            from accidents a
                     join accident_types at on a.accident_types_id = at.id
            WHERE a.id = ?
            """;

    private static final String SELECT_ACCIDENT_RULE_QUERY = """
            select r.id as rule_id, r.name as r_name
            from rules r
                     join accident_rules ar on r.id = ar.rule_id
            where ar.accident_id = ?
            """;

    private static final String SELECT_ALL_ACCIDENT_QUERY = """
                   select a.id,
                   a.name  as a_name,
                   a.text,
                   a.address,
                   at.id   as at_id,
                   at.name as at_name,
                   r.id    as r_id,
                   r.name  as r_name
            from accidents a
                     join accident_types at on a.accident_types_id = at.id
                     left join accident_rules ar on a.id = ar.accident_id
                     left join rules r on ar.rule_id = r.id
                            """;

    private static final String INSERT_ACCIDENT_ID_RULE_ID_QUERY = """
            insert into accident_rules(accident_id, rule_id) values (?, ?)
            """;

    private static final String DELETE_ACCIDENT_BY_ID_QUERY = """
            delete from accidents where id = ?
            """;

    private static final String DELETE_RULE_QUERY = """
            delete from accident_rules where accident_id = ?
            """;

    private static final String INSERT_ACCIDENT_QUERY = """
            insert into accidents (name, text, address, accident_types_id) values (?, ?, ?, ?)
            """;

    public Accident save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            INSERT_ACCIDENT_QUERY,
                            Statement.RETURN_GENERATED_KEYS
                    );
            preparedStatement.setString(1, accident.getName());
            preparedStatement.setString(2, accident.getText());
            preparedStatement.setString(3, accident.getAddress());
            preparedStatement.setInt(4, accident.getType().getId());
            return preparedStatement;
        }, keyHolder);
        int accidentId = (Integer) keyHolder.getKeys().get("id");
        for (Rule rule : accident.getRules()) {
            jdbc.update(
                    INSERT_ACCIDENT_ID_RULE_ID_QUERY,
                    accidentId, rule.getId()
            );
        }
        return accident;
    }

    public Optional<Accident> findById(int id) {
        List<Accident> accidents = jdbc.query(SELECT_ACCIDENT_FIND_BY_ID_QUERY, ps -> ps.setInt(1, id),
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("a_name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("at_id"));
                    accidentType.setName(rs.getString("at_name"));
                    accident.setType(accidentType);
                    return accident;
                });

        Accident accident = accidents.get(0);
        List<Rule> rules = jdbc.query(SELECT_ACCIDENT_RULE_QUERY, ps -> ps.setInt(1, accident.getId()),
                (rs, rowNum) -> {
                    var rule = new Rule();
                    rule.setId(rs.getInt("rule_id"));
                    rule.setName(rs.getString("r_name"));
                    return rule;
                });
        accident.setRules(new HashSet<>(rules));
        return Optional.of(accident);
    }

    public boolean update(Accident accident) {
        jdbc.update(UPDATE_ACCIDENT_QUERY, accident.getName(), accident.getText(),
                accident.getAddress(), accident.getType().getId(), accident.getId()
        );

        jdbc.update(DELETE_ACCIDENT_RULE_WHERE_REQUIRED_ID, accident.getId());
        Set<Rule> rules = accident.getRules();
        boolean rsl = false;
        for (Rule rule : rules) {
            rsl = jdbc.update(INSERT_ACCIDENT_ID_RULE_ID_QUERY, accident.getId(), rule.getId()) > 0;
        }
        return rsl;
    }

    public boolean deleteById(int id) {
        jdbc.update(DELETE_RULE_QUERY, id);
        return jdbc.update(DELETE_ACCIDENT_BY_ID_QUERY, id) > 0;
    }

    public Collection<Accident> getAll() {
        Map<Integer, Accident> accidentsMap = new HashMap<>();
        jdbc.query(SELECT_ALL_ACCIDENT_QUERY, (rs) -> {
            while (rs.next()) {
                int accidentId = rs.getInt("id");
                Accident allAccident = accidentsMap.computeIfAbsent(accidentId, key -> {
                    Accident accidentAccidentType = new Accident();
                    try {
                        accidentAccidentType.setId(rs.getInt("id"));
                        accidentAccidentType.setName(rs.getString("a_name"));
                        accidentAccidentType.setText(rs.getString("text"));
                        accidentAccidentType.setAddress(rs.getString("address"));
                        accidentAccidentType.setId(rs.getInt("id"));
                        AccidentType accidentType = new AccidentType();
                        accidentType.setId(rs.getInt("at_id"));
                        accidentType.setName(rs.getString("at_name"));
                        accidentAccidentType.setType(accidentType);
                        accidentAccidentType.setRules(new HashSet<>());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return accidentAccidentType;
                });
                int ruleId = rs.getInt("r_id");
                if (ruleId != 0) {
                    Rule rule = new Rule();
                    rule.setId(ruleId);
                    rule.setName(rs.getString("r_name"));
                    allAccident.getRules().add(rule);
                }
            }
        });
        return accidentsMap.values();
    }
}
