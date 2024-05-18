package sit.or3.demo.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sit.or3.demo.entities.Status;
import sit.or3.demo.entities.Todo;

import java.util.List;


public interface TodoRepository extends JpaRepository<Todo, Integer> {
    @Transactional
    @Query("SELECT t FROM Todo t WHERE (:statuses IS NULL OR t.status IN :statuses) ORDER BY " +
            "CASE WHEN :sortBy = 'status.name' THEN t.status END ASC, " +
            "CASE WHEN :sortBy = '-status.name' THEN t.status END DESC, " +
            "CASE WHEN :sortBy = 'createdDate' THEN t.createdOn END ASC")
    List<Todo> findByStatusAndSort(@Param("statuses") List<String> filterStatuses, @Param("sortBy") String sortBy);
    List<Todo> findByStatus(Status status);

}
