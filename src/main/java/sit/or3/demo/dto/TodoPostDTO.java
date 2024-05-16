package sit.or3.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoPostDTO{
    private Integer id;
    private String title;
    private String assignees;
    private Integer status;
    private String description;
}
