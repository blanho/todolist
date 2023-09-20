package com.blanho.todolist.controller;

import com.blanho.todolist.domain.ToDoList;
import com.blanho.todolist.service.ToDoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class TodoControllerTest {

    @MockBean
    private ToDoService toDoService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private ToDoList toDoList;

    @BeforeEach
    void init() {
        toDoList = new ToDoList();
        toDoList.setId(1L);
        toDoList.setTitle("Task1");
    }

    @Test
    void getAll() throws Exception {

        List<ToDoList> todos = new ArrayList<>();
        todos.add(toDoList);

        when(toDoService.getAllToDoLists()).thenReturn(todos);

        this.mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(todos.size())));
    }

    @Test
    void getTodoById() throws Exception {
        when(toDoService.getToDoListById(anyLong())).thenReturn(toDoList);

        this.mockMvc.perform(get("/todos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(toDoList.getTitle())));
    }

    @Test
    void addTodo() throws Exception {
        when(toDoService.saveTodo(any(ToDoList.class))).thenReturn(toDoList);

        this.mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(toDoList.getTitle())));
    }

    @Test
    void updateTodo() throws Exception {

        when(toDoService.updateTodo(anyLong(), any(ToDoList.class))).thenReturn(toDoList);

        this.mockMvc.perform(put("/todos/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(toDoList.getTitle())));
    }

    @Test
    void deleteTodo() throws Exception {

        doNothing().when(toDoService).deleteTodo(anyLong());

        this.mockMvc.perform(delete("/todos/{id}", 1L))
                .andExpect(status().isOk());

    }


}