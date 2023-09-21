package com.blanho.todolist.service;

import com.blanho.todolist.dto.media.MediaDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {
    public String storeFile(MultipartFile file, Long id);
    public MediaDto uploadFile(MultipartFile file, Long id);
    public List<MediaDto> uploadMultipleFiles(MultipartFile[] files, Long id);
}
