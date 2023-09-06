package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentRuleHibernate {

    private final SessionFactory sf;

    public HashSet<Rule> findAll() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            List<Rule> list = session.createQuery("from Rule", Rule.class)
                    .list();
            HashSet<Rule> rsl = new HashSet<>(list);
            session.getTransaction().commit();
            return rsl;
        }
    }

    public Optional<Rule> findById(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query<Rule> query = session.createQuery(
                    "from Rule as a where a.id=:fId", Rule.class);
            query.setParameter("fId", id);
            Rule rsl = query.uniqueResult();
            session.getTransaction().commit();
            return Optional.ofNullable(rsl);
        }
    }
}