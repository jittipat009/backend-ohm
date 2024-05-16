package sit.or3.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "itbkk_or3")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "assignees")
    private String assignees;

    @JsonProperty("status")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    @Column(updatable = false,insertable = false)
    private ZonedDateTime createdOn;

    @Column(updatable = false,insertable = false)
    private ZonedDateTime updatedOn;

}
