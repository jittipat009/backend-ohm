package sit.or3.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoByIdDTO {
    private Integer id;
    private String title;
    private String description;
    private String assignees;
    private String status;
    private String createdOn;
    private String updatedOn;

    // Getters and setters
}
