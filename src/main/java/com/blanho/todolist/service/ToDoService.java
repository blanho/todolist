package com.blanho.todolist.service;
import com.blanho.todolist.dto.todolist.ToDoListDto;
import com.blanho.todolist.dto.todolist.ToDoListResponse;
import java.util.List;

public interface ToDoService {
    ToDoListResponse createToDoList(ToDoListDto toDoListDto, String email);
    List<ToDoListResponse> getAllToDoLists();
    ToDoListResponse updateToDoList(ToDoListDto toDoListDto, Long id, String email);
    ToDoListResponse getToDoListById(Long id, String email);
    void deleteToDoListById(Long id, String email);
    List<ToDoListResponse> getTodoByCategory(Long categoryId);
}
