package ru.job4j.accidents.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accidents")
public class Accident {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String text;
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accident_types_id")
    private AccidentType type;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "accident_rules",
            joinColumns = { @JoinColumn(name = "accident_id", nullable = false, updatable = false, insertable = false) },
            inverseJoinColumns = { @JoinColumn(name = "rule_id", nullable = false, updatable = false, insertable = false) })
    private Set<Rule> rules;
}