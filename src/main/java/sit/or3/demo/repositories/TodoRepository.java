package sit.or3.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.or3.demo.entities.Status;
import sit.or3.demo.entities.Todo;

import java.util.List;


public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findByStatus(Status status);

    List<Todo> findAllByOrderByStatusAsc();

    List<Todo> findByStatusIn(List<String> statuses);

    List<Todo> findByStatusInOrderByStatusAsc(List<String> statuses);

}
