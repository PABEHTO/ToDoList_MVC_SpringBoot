package com.apress.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "records")
public class Task {
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "state", nullable = false)
    private State state;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Task() {}

    public Task(String name, State state) {
        this.name = name;
        this.state = state;
    }

    public Task(String name) {
        this.name = name;
        this.state = State.ACTIVE;
    }

    public String getName() {
        return name;
    }

    public State getState() {
        return state;
    }

    public int getId() {
        return id;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }
}