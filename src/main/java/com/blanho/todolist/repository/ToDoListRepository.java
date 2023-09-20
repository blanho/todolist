package com.blanho.todolist.repository;
import com.blanho.todolist.domain.ToDoList;
import com.blanho.todolist.dto.todolist.ToDoListResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {
    List<ToDoList> findByCategoryId(Long categoryId);
}
