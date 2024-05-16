package sit.or3.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.or3.demo.entities.Status;
import sit.or3.demo.service.StatusService;

import java.util.List;

@CrossOrigin(origins = {"http://ip23or3.sit.kmutt.ac.th", "http://localhost:5173"})
@RestController
@RequestMapping("/v2/statuses")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping("")
    public ResponseEntity<List<Status>> getAllStatuses() {
        List<Status> statuses = statusService.getAllStatuses();
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> getStatus(@PathVariable Integer id) {
        Status status = statusService.getStatus(id);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
        Status createdStatus = statusService.createStatus(status);
        return new ResponseEntity<>(createdStatus, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Status> updateStatus(@PathVariable Integer id, @RequestBody Status status) {
        Status updatedStatus = statusService.updateStatus(id, status);
        return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Integer id) {
        return statusService.deleteStatus(id);
    }

    @DeleteMapping("/{oldStatusId}/{newStatusId}")
    public ResponseEntity<String> deleteAndTransferStatus(@PathVariable Integer oldStatusId, @PathVariable Integer newStatusId) {
        return statusService.deleteAndTransferStatus(oldStatusId, newStatusId);
    }
}