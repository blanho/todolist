package com.blanho.todolist.integration;

import com.blanho.todolist.domain.ToDoList;
import com.blanho.todolist.repository.ToDoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoIntegrationTest {
    @LocalServerPort
    private int port;
    private String baseUrl = "http://localhost";


    private static RestTemplate restTemplate;
    @Autowired
    private ToDoRepository toDoRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void beforeSetup() {
        baseUrl = baseUrl + ":" + port + "/todos";
    }

    @AfterEach
    public void afterSetup() {
        toDoRepository.deleteAll();
    }

    @Test
    void shouldCreateTodoTest() {
       ToDoList toDoList = new ToDoList();
       toDoList.setTitle("Task1");
       ToDoList newTodoList = restTemplate.postForObject(baseUrl, toDoList, ToDoList.class);

       assertNotNull(newTodoList);
       assertThat(newTodoList.getId()).isNotNull();
    }

    @Test
    void shouldFetchTodosTest() {
        ToDoList toDoList = new ToDoList();
        toDoList.setTitle("Task1");

        restTemplate.postForObject(baseUrl, toDoList, ToDoList.class);
        List<ToDoList> lists = restTemplate.getForObject(baseUrl, List.class);

        assertThat(lists.size()).isEqualTo(1);
    }

    @Test
    void shouldFetchOneTodo() {
        ToDoList toDoList = new ToDoList();
        toDoList.setTitle("Task1");

        toDoList = restTemplate.postForObject(baseUrl, toDoList, ToDoList.class);
        ToDoList existingTodo = restTemplate.getForObject(baseUrl + "/" + toDoList.getId(), ToDoList.class);

        assertNotNull(existingTodo);
        assertThat(toDoList.getId()).isEqualTo(existingTodo.getId());
    }

    @Test
    void deleteOneTodo() {
        ToDoList toDoList = new ToDoList();
        toDoList.setTitle("Task1");

        toDoList = restTemplate.postForObject(baseUrl, toDoList, ToDoList.class);
        restTemplate.delete(baseUrl + "/" + toDoList.getId());

        int count = toDoRepository.findAll().size();
        assertEquals(0, count);
    }

    @Test
    void shouldUpdateOneToDo() {
        ToDoList toDoList = new ToDoList();
        toDoList.setTitle("Task1");

        toDoList = restTemplate.postForObject(baseUrl, toDoList, ToDoList.class);
        toDoList.setTitle("Task2");

        restTemplate.put(baseUrl + "/{id}", toDoList, toDoList.getId());
        ToDoList existingTodoList = restTemplate.getForObject(baseUrl + "/" + toDoList.getId(), ToDoList.class);

        assertNotNull(existingTodoList);
        assertThat(toDoList.getTitle()).isEqualTo(existingTodoList.getTitle());
    }
}
