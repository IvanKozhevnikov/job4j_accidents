package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class AccidentTypeJdbcTemplate {

    private static final String SELECT_ALL_ACCIDENT_TYPE_QUERY = "select id, name from accident_types";

    private static final String SELECT_ACCIDENT_TYPE_BY_ID_QUERY = "select id, name from accident_types where id = ?";

    private final JdbcTemplate jdbc;

    public List<AccidentType> findAll() {
        List<AccidentType> types = new ArrayList<>();
        jdbc.query(SELECT_ALL_ACCIDENT_TYPE_QUERY, resultSet -> {
            do {
                AccidentType type = new AccidentType();
                type.setId(resultSet.getInt("id"));
                type.setName(resultSet.getString("name"));
                types.add(type);
            } while (resultSet.next());
        });
        return types;
    }

    public Optional<AccidentType> findById(int id) {
        AccidentType type = jdbc.queryForObject(SELECT_ACCIDENT_TYPE_BY_ID_QUERY, new Object[]{id}, (rs, rowNum) -> {
            int typeId = rs.getInt("id");
            String typeName = rs.getString("name");
            return new AccidentType(typeId, typeName);
        });
        return Optional.ofNullable(type);
    }
}