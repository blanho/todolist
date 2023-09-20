package com.blanho.todolist.repository;

import com.blanho.todolist.domain.ToDoList;
import com.blanho.todolist.exception.ToDoNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class ToDoRepositoryTest {


    private ToDoRepository todoRepo;


    // Execute before each test case is started
    @BeforeEach
    void init() {

    }

    // Execute after each test case is completed
    @AfterEach
    void tearDown() {
        todoRepo.deleteAll();
    }

    @Test
    @DisplayName("It should save to do list to database")
    public void givenToDoList_whenSaveToDoList_thenReturnSavedToDoList() {
        // Given
        ToDoList toDoList = new ToDoList();
        toDoList.setTitle("New Task 1");
        toDoList.setDescription("New Task 1");

        // When
        ToDoList savedToDoList = todoRepo.save(toDoList);

        // Then
        assertNotNull(savedToDoList);
        assertThat(savedToDoList.getId()).isNotEqualTo(null);
    }

    @Test
    @DisplayName("It should return todos with size of 2")
    void given2TodoLists_whenSave2TodoLists_thenReturn2ToDoLists() {

        // Given
        ToDoList toDoList = new ToDoList();
        toDoList.setTitle("New Task 2");
        toDoList.setDescription("New Task 2");
        todoRepo.save(toDoList);

        ToDoList toDoList1 = new ToDoList();
        toDoList.setTitle("New Task 3");
        toDoList.setDescription("New Task 3");
        todoRepo.save(toDoList1);

        // When
        List<ToDoList> todos = todoRepo.findAll();

        // Then
        assertNotNull(todos);
        assertThat(todos).isNotNull();
        assertEquals(2, todos.size());
    }

    @Test
    void getTodoById() {
        // Given
        ToDoList toDoList = new ToDoList();
        toDoList.setTitle("New Task 4");
        toDoList.setDescription("New Task 4");
        todoRepo.save(toDoList);

        // When
        Optional<ToDoList> existingToDoList = todoRepo.findById(toDoList.getId());

        // Then
        assertTrue(existingToDoList.isPresent());
        assertNotNull(existingToDoList);
        assertEquals("New Task 4", existingToDoList.get().getTitle());
    }

    @Test
    @DisplayName("It should update todo with title")
    void updateTodo() {
        // Given
        ToDoList toDoList = new ToDoList();
        toDoList.setTitle("New Task");
        toDoList.setDescription("New Description");
        todoRepo.save(toDoList);

        // When
        ToDoList existingToDoList = todoRepo.findById(toDoList.getId())
                .orElseThrow(() -> new ToDoNotFoundException(""));
        existingToDoList.setTitle("Task");
        ToDoList newTodoList = todoRepo.save(existingToDoList);

        // Then
        assertEquals("Task", newTodoList.getTitle());
        assertEquals("New Description", newTodoList.getDescription());
    }

    @Test
    void deleteTodo() {
        // Given
        ToDoList toDoList = new ToDoList();
        toDoList.setTitle("New Task");
        todoRepo.save(toDoList);
        Long id = toDoList.getId();

        // When
        todoRepo.delete(toDoList);
        boolean existingTodo = todoRepo.existsById(id);

        // Then
        assertThat(existingTodo).isFalse();
    }

    @Test
    void findTodoByTitle() {
        // Given
        ToDoList toDoList = new ToDoList();
        toDoList.setTitle("New Task");
        todoRepo.save(toDoList);

        // When
        List<ToDoList> existingToDoList = todoRepo.findToDoListsByTitle("New Task");

        // Then
        assertNotNull(existingToDoList);
        assertThat(existingToDoList.size()).isEqualTo(1);
    }

}