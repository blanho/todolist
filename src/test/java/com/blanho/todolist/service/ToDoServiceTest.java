package com.blanho.todolist.service;

import com.blanho.todolist.domain.ToDoList;
import com.blanho.todolist.repository.ToDoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToDoServiceTest {

    /**
     * We don't need to test real todoRepo when testing todoService.
     * We just mock todoRepo implementation inside todoService
     * The benefit of mocking todoRepo inside toDoService that unit test is so fast
     * We don't need to bring up database and set up the table
     *
     */

    @InjectMocks
    private ToDoService toDoService;

    @Mock
    private ToDoRepository todoRepo;

    private ToDoList todoList;

    @BeforeEach
    void init() {
        todoList = new ToDoList();
        todoList.setId(1L);
        todoList.setTitle("Task 1");
    }

    @Test
    @DisplayName("Should save todo to database")
    void save() {
        // Given


        // When
        when(todoRepo.save(any(ToDoList.class))).thenReturn(todoList);
        ToDoList newToDoList = toDoService.saveTodo(todoList);

        // Then
        assertNotNull(newToDoList);
        assertThat(newToDoList.getTitle()).isEqualTo(todoList.getTitle());
    }

    @Test
    @DisplayName("Should return list of todos")
    void getAllTodos() {
        // Given


        // When
        List<ToDoList> todos = new ArrayList<>();
        todos.add(todoList);

        when(todoRepo.findAll()).thenReturn(todos);
        List<ToDoList> listOfTodos = toDoService.getAllTodos();

        // Then
        assertNotNull(listOfTodos);
        assertEquals(1, listOfTodos.size());
    }

    @Test
    @DisplayName("Should return a todo")
    void getTodoById() {


        when(todoRepo.findById(anyLong())).thenReturn(Optional.of(todoList));

        ToDoList existingTodo = toDoService.getToDoListById(todoList.getId());

        assertNotNull(existingTodo);
        assertThat(existingTodo.getId()).isEqualTo(todoList.getId());
    }

    @Test
    @DisplayName("Should throw the exception")
    void getTodoByIdForException() {

        when(todoRepo.findById(1L)).thenReturn(Optional.of(todoList));

        assertThrows(RuntimeException.class, () -> {
            toDoService.getToDoListById(2L);
        });
    }

    @Test
    @DisplayName("Should update the movie into the database")
    void updateTodo() {


        when(todoRepo.findById(anyLong())).thenReturn(Optional.of(todoList));
        todoList.setTitle("Task 2");
        when(todoRepo.save(any(ToDoList.class))).thenReturn(todoList);
        ToDoList existingTodo = toDoService.updateTodo(1L, todoList);

        assertNotNull(existingTodo);
        assertEquals("Task 2", existingTodo.getTitle());
    }

    @Test
    @DisplayName("Should delete the todo")
    void deleteTodo() {


        when(todoRepo.findById(anyLong())).thenReturn(Optional.of(todoList));
        doNothing().when(todoRepo).delete(any(ToDoList.class));

        toDoService.deleteTodo(1L);
        verify(todoRepo, times(1)).delete(todoList);
    }

}