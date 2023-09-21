package com.blanho.todolist.dto.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaDto {
    private String fileName;
    private String fileType;
    private String uploadDir;
}
