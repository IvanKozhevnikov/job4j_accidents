//package ru.job4j.accidents.service.map;
//
//import lombok.AllArgsConstructor;
//import ru.job4j.accidents.model.Rule;
//import ru.job4j.accidents.repository.hibernate.AccidentRuleHibernate;
//import ru.job4j.accidents.service.impl.RuleService;
//
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//
//
//@AllArgsConstructor
//public class SimpleRuleService implements RuleService {
//
//    private final AccidentRuleHibernate ruleRepository;
//
//    @Override
//    public Optional<Rule> findById(int id) {
//        return ruleRepository.findById(id);
//    }
//
//    @Override
//    public HashSet<Rule> findAll() {
//        return ruleRepository.findAll();
//    }
//
//    @Override
//    public Set<Rule> findByIds(String[] ids) {
//        Set<Rule> rules = new HashSet<>();
//        for (String i : ids) {
//            findById(Integer.parseInt(i)).ifPresent(rules::add);
//        }
//        return rules;
//    }
//}
