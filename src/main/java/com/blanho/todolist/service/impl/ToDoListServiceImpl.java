package com.blanho.todolist.service.impl;
import com.blanho.todolist.domain.Category;
import com.blanho.todolist.domain.ToDoList;
import com.blanho.todolist.domain.User;
import com.blanho.todolist.dto.todolist.ToDoListDto;
import com.blanho.todolist.dto.todolist.ToDoListResponse;
import com.blanho.todolist.exception.ResourceNotFoundException;
import com.blanho.todolist.exception.ToDoApiException;
import com.blanho.todolist.repository.CategoryRepository;
import com.blanho.todolist.repository.ToDoListRepository;
import com.blanho.todolist.repository.UserRepository;
import com.blanho.todolist.service.ToDoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ToDoListServiceImpl implements ToDoService {
    private final ToDoListRepository toDoListRepo;
    private final CategoryRepository categoryRepo;
    private final ModelMapper mapper;
    private final UserRepository userRepository;

    public ToDoListServiceImpl(
            ToDoListRepository toDoListRepo,
            ModelMapper mapper,
            CategoryRepository categoryRepo,
            UserRepository userRepository
    ) {
        this.toDoListRepo = toDoListRepo;
        this.mapper = mapper;
        this.categoryRepo = categoryRepo;
        this.userRepository = userRepository;
    }

    @Override
    public ToDoListResponse createToDoList(ToDoListDto toDoListDto, String email) {
        Category category = categoryRepo.findById(toDoListDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", toDoListDto.getCategoryId()));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        ToDoList toDoList = mapToEntity(toDoListDto);
        toDoList.setCategory(category);
        toDoList.setAuthor(user);
        ToDoList newToDoList = this.toDoListRepo.save(toDoList);
        return mapToDto(newToDoList);
    }

    @Override
    public List<ToDoListResponse> getAllToDoLists() {
        List<ToDoList> toDoLists = this.toDoListRepo.findAll();
        return toDoLists.stream().map((this::mapToDto))
                .collect(Collectors.toList());
    }

    @Override
    public ToDoListResponse updateToDoList(ToDoListDto toDoListDto, Long id, String email) {
        ToDoList toDoList = toDoListRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TodoList", "id", id));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        boolean isAuthor = Objects.equals(toDoList.getAuthor().getId(), user.getId());
        if (!isAuthor) {
            throw new ToDoApiException(HttpStatus.UNAUTHORIZED, "You are not allowed to delete this todo");
        }
        Category category = categoryRepo.findById(toDoListDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        toDoList = mapToEntity(toDoListDto);
        toDoList.setCategory(category);

        ToDoList updatedToDoList = toDoListRepo.save(toDoList);
        return mapToDto(updatedToDoList);
    }

    @Override
    public ToDoListResponse getToDoListById(Long id, String email) {
        return null;
    }


    @Override
    public void deleteToDoListById(Long id, String email) {
        ToDoList toDoList = toDoListRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TodoList", "id", id));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        boolean isAuthor = Objects.equals(toDoList.getAuthor().getId(), user.getId());
        if (!isAuthor) {
            throw new ToDoApiException(HttpStatus.UNAUTHORIZED, "You are not allowed to delete this todo");
        }
        toDoListRepo.delete(toDoList);
    }

    @Override
    public List<ToDoListResponse> getTodoByCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<ToDoList> todos = toDoListRepo.findByCategoryId(categoryId);
        return todos.stream().map((todo) -> mapToDto(todo)).collect(Collectors.toList());
    }

    private ToDoListResponse mapToDto(ToDoList toDoList) {
        return mapper.map(toDoList, ToDoListResponse.class);
    }

    private ToDoList mapToEntity(ToDoListDto toDoListDto) {
        return mapper.map(toDoListDto, ToDoList.class);
    }
}
