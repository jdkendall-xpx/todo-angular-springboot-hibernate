package com.xpanxion.todo.repositories;

import com.xpanxion.todo.domain.TodoEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntry, Long> {
    List<TodoEntry> findByComplete(boolean complete);
    TodoEntry findById(Integer id);
}
