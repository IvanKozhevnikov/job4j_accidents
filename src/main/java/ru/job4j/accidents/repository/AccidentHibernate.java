package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHibernate {
    private final SessionFactory sf;

    public Accident save(Accident accident) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(accident);
            session.getTransaction().commit();
        } catch (Exception exception) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return accident;
    }

    public List<Accident> getAll() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            List<Accident> rsl = session.createQuery("from Accident", Accident.class)
                    .list();
            session.getTransaction().commit();
            return rsl;
        }
    }

    public boolean update(Accident accident) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.merge(accident);
            session.getTransaction().commit();
            return true;
        } catch (Exception exception) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    public boolean deleteById(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Accident accident = new Accident();
            accident.setId(id);
            session.delete(accident);
            session.getTransaction().commit();
            return true;
        } catch (Exception exception) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    public Optional<Accident> findById(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query<Accident> query = session.createQuery(
                    "from Accident as a where a.id=:fId", Accident.class);
            query.setParameter("fId", id);
            Accident rsl = query.uniqueResult();
            session.getTransaction().commit();
            return Optional.ofNullable(rsl);
        }
    }
}