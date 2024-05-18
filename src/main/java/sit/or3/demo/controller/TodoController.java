package sit.or3.demo.controller;


import io.swagger.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import sit.or3.demo.dto.TodoByIdDTO;
import sit.or3.demo.dto.TodoDTO;
import sit.or3.demo.dto.TodoPostDTO;
import sit.or3.demo.dto.TodoResponseDTO;
import sit.or3.demo.entities.Todo;
import sit.or3.demo.service.TodoService;
import springfox.documentation.swagger2.mappers.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@CrossOrigin(origins = {"http://ip23or3.sit.kmutt.ac.th", "http://localhost:5173" , "http://intproj23.sit.kmutt.ac.th"})
@RestController
@RequestMapping("/v2/tasks")
public class TodoController {
    @Autowired
    private TodoService service;

    @GetMapping("")
    public ResponseEntity<List<TodoDTO>> getTasks(@RequestParam(required = false) List<String> filterStatuses, @RequestParam(defaultValue = "createdDate") String sortBy) {
        try {
            List<TodoDTO> task = service.getTasks(filterStatuses, sortBy);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public TodoByIdDTO getTodoById(@PathVariable Integer id) {
        return  service.getTodo(id);
    }

    @PostMapping("")
    public ResponseEntity<TodoResponseDTO> addNewTodo(@RequestBody TodoPostDTO todoPostDTO){
        TodoResponseDTO createdTodo = service.createNewTodo(todoPostDTO);
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Integer id, @RequestBody TodoByIdDTO todoByIdDTO){
        Todo updatedTodo = service.updateTodo(id, todoByIdDTO);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void removeTodo(@PathVariable String id){
        service.removeTodo(id);
    }

//    @GetMapping("")
//    public ResponseEntity<List<TodoDTO>> getAllTodo(){
//        try {
//            List<TodoDTO> todos = service.getAllTodo();
//            return new ResponseEntity<>(todos, HttpStatus.OK);
//        } catch (Exception e) {
//            // Log the exception
//            System.err.println(e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
    }

}
