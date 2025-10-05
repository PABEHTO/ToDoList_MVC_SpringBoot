package com.apress.repository;

import com.apress.entity.State;
import com.apress.entity.Task;
import com.apress.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Modifying
    @Query("UPDATE Task SET state = :state WHERE id = :id")
    void update(@Param("id") int id, @Param("state")State state);

    List<Task> findAllByState(State state);

}
