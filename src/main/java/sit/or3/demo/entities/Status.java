package sit.or3.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false, length=50)
    private Integer id;

    @Column(name="name")
    private String name;

    @JsonProperty("description")
    @Column(name="description", length=200)
    private String statusDescription;
}