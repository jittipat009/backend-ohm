package sit.or3.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import sit.or3.demo.entities.Status;
import sit.or3.demo.entities.Todo;
import sit.or3.demo.repositories.StatusRepository;
import sit.or3.demo.repositories.TodoRepository;

import java.util.List;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private TodoRepository todoRepository;

    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    public Status getStatus(Integer id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Status not found"));
    }

    public Status createStatus(Status status) {
        if (status.getName() == null || status.getName().trim().isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Status name cannot be null or empty");
        }

        // Trim the name and description
        status.setName(status.getName().trim());
        if (status.getStatusDescription() != null) {
            status.setStatusDescription(status.getStatusDescription().trim());
        }

        try {
            return statusRepository.save(status);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while creating the status");
        }
    }

    public Status updateStatus(Integer id, Status newStatus) {
        try {
            if (id == 1 && !newStatus.getName().equals("No Status")) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Cannot change the name of the 'No Status' status");
            }

            if (!statusRepository.existsById(id)) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Status not found");
            }

            if (newStatus.getName() == null || newStatus.getName().trim().isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Status name cannot be null or empty");
            }

            newStatus.setName(newStatus.getName().trim());
            if (newStatus.getStatusDescription() != null) {
                newStatus.setStatusDescription(newStatus.getStatusDescription().trim());
            }

            return statusRepository.findById(id)
                    .map(status -> {
                        status.setName(newStatus.getName());
                        status.setStatusDescription(newStatus.getStatusDescription());
                        return statusRepository.save(status);
                    })
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Status not found"));
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while updating the status");
        }
    }

    public ResponseEntity<Void> deleteStatus(Integer id) {
        if (!statusRepository.existsById(id)) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "No Status found with id :" + id);
        }

        Status status = statusRepository.findById(id).get();

        if (status.getName().equals("No Status")) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Can't delete");
        }

        statusRepository.delete(status);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> deleteAndTransferStatus(Integer oldStatusId, Integer newStatusId) {
        if (oldStatusId.equals(newStatusId)) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Old status and new status cannot be the same");
        }

        Status oldStatus = statusRepository.findById(oldStatusId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Old status not found"));

        Status newStatus = statusRepository.findById(newStatusId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "New status not found"));

        List<Todo> todos = todoRepository.findByStatus(oldStatus);
        for (Todo todo : todos) {
            todo.setStatus(newStatus);
            todoRepository.save(todo);
        }

        statusRepository.delete(oldStatus);
        return ResponseEntity.ok("Transfer passed and task has been deleted");
    }

}