package com.blanho.todolist.controller;
import com.blanho.todolist.dto.todolist.ToDoListResponse;
import com.blanho.todolist.dto.todolist.ToDoListDto;
import com.blanho.todolist.service.ToDoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@Validated
public class TodoController {
    private final ToDoService todoService;
    @Autowired
    public TodoController(ToDoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping()
    public ResponseEntity<List<ToDoListResponse>> getAll() {
        List<ToDoListResponse> todos = todoService.getAllToDoLists();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDoListResponse> getTodoById(@PathVariable(name = "id") Long id, Authentication authentication) {
        return ResponseEntity.ok(todoService.getToDoListById(id, authentication.getName()));
    }

    @PostMapping()
    public ResponseEntity<ToDoListResponse> createToDo(@RequestBody @Valid ToDoListDto todo, Authentication authentication) {
        ToDoListResponse toDoList = todoService.createToDoList(todo, authentication.getName());
       return new ResponseEntity<>(toDoList, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDoListResponse> updateTodo(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody ToDoListDto todo,
            Authentication authentication
    ) {
        ToDoListResponse toDoList = todoService.updateToDoList(todo, id, authentication.getName());
        return new ResponseEntity<>(toDoList, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(
            @PathVariable(name = "id") Long id,
            Authentication authentication
    ) {
        todoService.deleteToDoListById(id, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ToDoListResponse>> getTodoByCategory(@PathVariable("id") Long categoryId) {
        List<ToDoListResponse> toDoLists = todoService.getTodoByCategory(categoryId);
        return ResponseEntity.ok(toDoLists);
    }
}
