package com.blanho.todolist.dto.todolist;

import com.blanho.todolist.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoListDto {
    private String title;
    private String description;
    private Status status;
    private Date duedate;
    private Long categoryId;
}
