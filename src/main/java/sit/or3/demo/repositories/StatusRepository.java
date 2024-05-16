package sit.or3.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.or3.demo.entities.Status;
import sit.or3.demo.entities.Todo;

import java.util.List;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Integer>{
    Optional<Status> findByName(String name);



}
