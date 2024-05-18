package sit.or3.demo.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import org.springframework.web.server.ResponseStatusException;
import sit.or3.demo.dto.*;
import sit.or3.demo.entities.Status;
import sit.or3.demo.entities.Todo;
import sit.or3.demo.repositories.StatusRepository;
import sit.or3.demo.repositories.TodoRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    @Autowired
    private StatusRepository statusRepository;
//    public List<Todo> getAllTodo() {
//        return repository.findAll();
//    }


    public List<TodoDTO> getTasks(List<String> filterStatuses, String sortBy) {
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "id";
        }
        if (filterStatuses == null) {
            filterStatuses = new ArrayList<>();
        }
        return repository.findByStatusAndSort(filterStatuses, sortBy).stream().map(todo -> {
                    TodoDTO dto = new TodoDTO();
                    dto.setId(todo.getId());
                    dto.setTitle(todo.getTitle());
                    dto.setAssignees(todo.getAssignees());
                    if (todo.getStatus() != null) {
                        dto.setStatus(todo.getStatus().getName());
                    } else {
                        dto.setStatus(null);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
    public List<TodoDTO> getAllTodo() {
        return repository.findAll().stream()
                .map(todo -> {
                    TodoDTO dto = new TodoDTO();
                    dto.setId(todo.getId());
                    dto.setTitle(todo.getTitle());
                    dto.setAssignees(todo.getAssignees());
                    if (todo.getStatus() != null) {
                        dto.setStatus(todo.getStatus().getName());
                    } else {
                        dto.setStatus(null);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }


    public TodoByIdDTO getTodo(Integer id) {
        Todo todo = repository.findById(Integer.parseInt(String.valueOf(id))).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Task with id " + id + " not found."));

        TodoByIdDTO response = new TodoByIdDTO();
        response.setId(todo.getId());
        response.setDescription(todo.getDescription());
        response.setTitle(todo.getTitle());
        response.setAssignees(todo.getAssignees());
        if (todo.getStatus() != null) {
            response.setStatus(todo.getStatus().getName()); // set the status name
        }
        if (todo.getCreatedOn() != null) {
            response.setCreatedOn(todo.getCreatedOn().toString()); // set the createdOn
        }
        if (todo.getUpdatedOn() != null) {
            response.setUpdatedOn(todo.getUpdatedOn().toString()); // set the updatedOn
        }

        return response;
    }


    @Transactional
    public TodoResponseDTO createNewTodo(TodoPostDTO todoPostDTO) {
        Status status = null;
        if (todoPostDTO.getStatus() != null) {
            // Find the status by id
            status = statusRepository.findById(todoPostDTO.getStatus())
                    .orElse(null);
            if (status == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Status not found");
            }
        } else {
            // Set a default status if none is provided
            status = statusRepository.findByName("no status")
                    .orElse(null);
            if (status == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Default status not found");
            }
        }

        Todo todo = new Todo();
        todo.setTitle(todoPostDTO.getTitle() != null ? todoPostDTO.getTitle().trim() : null);
        // Check if description is empty string and if so, set it to null
        todo.setDescription((todoPostDTO.getDescription() != null && !todoPostDTO.getDescription().isEmpty()) ? todoPostDTO.getDescription().trim() : null);
        // Check if assignees is empty string and if so, set it to null
        todo.setAssignees((todoPostDTO.getAssignees() != null && !todoPostDTO.getAssignees().isEmpty()) ? todoPostDTO.getAssignees().trim() : null);
        todo.setStatus(status);
        todo.setCreatedOn(ZonedDateTime.now(ZoneId.of("Z"))); // set the createdOn to current time
        todo.setUpdatedOn(ZonedDateTime.now(ZoneId.of("Z"))); // set the updatedOn to current time

        todo = repository.save(todo);

        // Convert the saved Todo entity to TodoResponseDTO
        TodoResponseDTO response = new TodoResponseDTO();
        response.setId(todo.getId());
        response.setTitle(todo.getTitle());
        response.setDescription(todo.getDescription());
        response.setAssignees(todo.getAssignees());
        if (todo.getStatus() != null) {
            StatusNameDTO statusNameDTO = new StatusNameDTO();
            statusNameDTO.setName(todo.getStatus().getName());
            response.setStatus(statusNameDTO.getName());
        }

        return response;
    }


    @Transactional
    public void removeTodo(String id) {
        Todo todo = repository.findById(Integer.parseInt(id)).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Task Id" + id + "Does not exit !!!") {
                });
        repository.delete(todo);
    }

    @Transactional
    public Todo updateTodo(Integer id, TodoByIdDTO todoByIdDTO) {
        Todo existingTodo = repository.findById((id)).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Task Id" + id + "Does not exit !!!"));

        existingTodo.setTitle(todoByIdDTO.getTitle() != null ? todoByIdDTO.getTitle().trim() : null);
        existingTodo.setAssignees(todoByIdDTO.getAssignees() != null ? todoByIdDTO.getAssignees().trim() : null);
        existingTodo.setDescription(todoByIdDTO.getDescription() != null ? todoByIdDTO.getDescription().trim() : null);

        String statusName = (todoByIdDTO.getStatus() != null && !todoByIdDTO.getStatus().isEmpty()) ? todoByIdDTO.getStatus() : "No Status";
        Status status = statusRepository.findByName(statusName).orElse(null);
        if (status == null) {
            status = new Status();
            status.setName(statusName);
            statusRepository.save(status);
        }
        existingTodo.setStatus(status);

        existingTodo.setUpdatedOn(ZonedDateTime.now(ZoneId.of("Z"))); // set the updatedOn to current time
        return repository.save(existingTodo);
    }
}
