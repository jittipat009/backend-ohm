package sit.or3.demo.dto;

import lombok.Getter;
import lombok.Setter;
import sit.or3.demo.entities.Status;

@Getter
@Setter
public class TodoResponseDTO {
    private Integer id;
    private String title;
    private String description;
    private String assignees;
    private String status;
}