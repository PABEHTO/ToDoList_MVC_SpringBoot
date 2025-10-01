package com.apress.dao;

import com.apress.entity.State;
import com.apress.entity.Task;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;

@Repository
public class TaskDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Task> findAllTasks() {
        Query query = entityManager.createQuery("SELECT t FROM Task t ORDER BY t.id ASC");
        List<Task> tasks = query.getResultList();
        return tasks;
    }

    public void saveTask(Task task) {
        entityManager.persist(task);

    }

    public void finishTask(int id) {
        Query query = entityManager.createQuery("UPDATE Task t SET t.state = :state WHERE t.id = :id");
        query.setParameter("state", State.DONE);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public void deleteTask(int id) {
        Query query = entityManager.createQuery("DELETE FROM Task t WHERE t.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
