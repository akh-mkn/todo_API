package com.example.appApi.repository;

import com.example.appApi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

// this gives us all the basic database commands (save, findById, findAll, delete, etc.)
public interface TaskRepository extends JpaRepository<Task, Long> {
}
