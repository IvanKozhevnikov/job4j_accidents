package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeHibernate {

    private final SessionFactory sf;;

    public List<AccidentType> findAll() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            List<AccidentType> rsl = session.createQuery("from AccidentType", AccidentType.class)
                    .list();
            session.getTransaction().commit();
            return rsl;
        }
    }

    public Optional<AccidentType> findById(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query<AccidentType> query = session.createQuery(
                    "from AccidentType as a where a.id=:fId", AccidentType.class);
            query.setParameter("fId", id);
            AccidentType rsl = query.uniqueResult();
            session.getTransaction().commit();
            return Optional.ofNullable(rsl);
        }
    }
}
