package com.blanho.todolist.dto.todolist;

import com.blanho.todolist.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToDoListResponse {

   private Long id;


   private String title;


   private String description;


   private Status status;


   private Date duedate;


   private Date createdAt;


   private Date updatedAt;
}
